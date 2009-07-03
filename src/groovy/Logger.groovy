/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eirikb
 */

import org.codehaus.groovy.grails.commons.*
import org.apache.commons.logging.LogFactory

class Logger implements ParseListener {
    def parser
    def config
    def log

    public Logger() {
        config = ConfigurationHolder.config
        parser = new LogParser(new File(config.urt.qconsole.path), true, true)
        parser.addParseListener(this)
        config = ConfigurationHolder.config
        log = LogFactory.getLog("grails.app.task")
        RCon.rcon("rcon bigtext \"UrTStats is now running! Check out ^2www.urtstats.\"")
    }

    void execute() {
        parser.simpleParse()
    }

    void userInfo(id, userInfo) {
        def guid = userInfo.cl_guid
        def player = Player.findByGuid(guid)
        if (player == null) {
            player = new Player(guid:guid, ip:userInfo.ip, nick:userInfo.name, urtID:id)
        } else {
            player.setUrtID(id)
            player.setIp(userInfo.ip)
            player.setJoinGameTime(new Date())
        }

        player.addToPlayerLogs(new PlayerLog(startTime:new Date()))

        if (userInfo.gear != null) {
            userInfo.gear.each() {
                def item = Item.findBybinding(it)
                if (item != null) {
                    player.addToItems(item)
                }
            }
        }

        if(player.hasErrors() || !player.save(flush:true)) {
            log.error("Unable to persist: " + player.dump())
        } else {
            addPlayerToTeam(player, 0)
        }
    }

    private void addPlayerToTeam(player, teamID) {
        def team = Team.findByUrtID(teamID)
        if (team == null) {
            team = new Team(urtID:teamID)
            if(team.hasErrors() || !team.save(flush:true)) {
                log.error("Unable to persist: " + team.dump())
            }
        }
        if (player.team == null || player.team != team) {
            player.setTeam(team)
            if(team.hasErrors() || !team.save(flush:true)) {
                log.error("Unable to persist: " + team.dump())
            }
        }
    }

    void userInfoChange(id, userInfo) {
        def player = Player.findByUrtID(id)
        if (player != null) {
            def urtID = Integer.parseInt(userInfo.t)
            def same = player.team.urtID == urtID
            if (player.team.urtID != urtID) {
                addPlayerToTeam(player, urtID)
            }
            RCon.rcon("rcon tell " + player.getUrtID() + "\"^7 Welcome to UrTStats server. Your PIN is ^1" +
                player.getPin() + "^7. Use it to actie your account on ^2www.urtstats.net\"")
        } else {
            log.error("Unkown player: " + id + ". " + userInfo.dump())
        }
    }

    void leave(id) {
        def player = Player.findByUrtID(id)
        if (player != null) {
            player.team = null;
            player.urtID = -1;
            if(player.hasErrors() || !player.save(flush:true)) {
                log.error("Unable to persist: " + player.dump())
            }
            def playerLog = PlayerLog.findByPlayerAndEndTimeIsNull(player)
            if (playerLog != null) {
                playerLog.setEndTime(new Date())
                if(playerLog.hasErrors() || !playerLog.save(flush:true)) {
                    log.error("Unable to persist: " + player.dump())
                }
            } else {
                log.error("Unable to find playerlog for player " + player.dump())
            }
        } else {
            log.error("player not found: " + id)
        }
    }

    void kill(killerID, killedID, type) {
        def killer = Player.findByUrtID(killerID)
        def killed = Player.findByUrtID(killedID)
        if (killer != null && killed != null) {
            def friendlyfire = killer.team == killed.team
            def death = DeathCause.findByUrtID(type)
            if (death != null) {
                def kill = new Kill(killer:killer, killed:killed, friendlyfire:friendlyfire, deathCause:death)
                if(kill.hasErrors() || !kill.save(flush:true)) {
                    log.error("Unable to persist: " + kill.dump())
                }

                if (!friendlyfire) {
                    def kills = Kill.countBykiller(killer)
                    def deaths = Kill.countByKilled(killed)
                    def gameKills = Kill.countByKillerAndCreateTimeGreaterThan(killer, killer.getJoinGameTime())
                    def gameDeaths = Kill.countByKilledAndCreateTimeGreaterThan(killed, killer.getJoinGameTime())

                    killer.exp += (killed.level - killer.level > 0 ? killed.level - killer.level : 1) *
                    (((kills + 1) / (deaths + 1)) + ((gameKills + 1) / (gameDeaths + 1)) / 2)

                    if (killer.exp > killer.nextlevel) {
                        killer.level++;
                        killer.nextlevel = killer.exp * 1.2 + Math.sqrt(killer.exp)
                        RCon.rcon("rcon bigtext \"Congratulations ^2" + killer.nick.trim() + "^1 you are now level ^2" + killer.level + '"')
                    }
                    if(killer.hasErrors() || !killer.save(flush:true)) {
                        log.error("Unable to persist: " + killer.dump())
                    }
                }
            } else {
                log.error("Unknown death for type: " + type)
            }
        }
    }

    void chat(id, teammessage, message) {
        def player = Player.findByUrtID(id)
        def chat = new Chat(player:player, teamMessage:teammessage, message:message)
        if(chat.hasErrors() || !chat.save(flush:true)) {
            log.error("Unable to persist: " + chat.dump())
        }
    }

    void hit(hitterID, victimID, hitpoint, weapon) {
        def hitter = Player.findByUrtID(hitterID)
        def victim = Player.findByUrtID(victimID)
        def item = Item.findByUrtID(weapon)
        if (hitter != null && victim != null && item != null) {
            def hit = new Hit(hitter:hitter, victim:victim, friendlyfire:(hitter.team == victim.team),
                hitpoint:hitpoint, item:item)
            if(hit.hasErrors() || !hit.save(flush:true)) {
                log.error("Unable to persist: " + hit.dump())
            }
        } else {
            log.error("Could not create hit. One of the following are null: hitter: " +
                hitter + ". victim: " + victim + ". item: " + item +
                ". Original(" + hitterID + ", " + victimID + ", " + weapon + ")")
        }
    }

    void initRound(roundInfo) {
        def players = Player.findAllByUrtIDGreaterThan(-1)
        players.each() { it.setJoinGameTime(new Date()) }
    }

    void serverStart() {
        def players = Player.findAllByUrtIDGreaterThanEquals(0)
        players.each() { leave(it.getUrtID()) }
    }
}

