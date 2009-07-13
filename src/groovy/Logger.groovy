import org.codehaus.groovy.grails.commons.*
import org.apache.commons.logging.LogFactory
import org.jsecurity.authz.SimpleRole

class Logger implements ParseListener {
    def parser
    def config
    def log
    def synced = false

    public Logger() {
        config = ConfigurationHolder.config
        parser = new LogParser(new File(config.urt.qconsole.path), true)
        parser.addParseListener(this)
        log = LogFactory.getLog("grails.app.task")
        RCon.rcon("rcon bigtext \"UrTStats is now running! Check out ^2www.urtstats.\"")
        sync()
    }

    void execute() {
        if (synced) {
            parser.parse()
        }
    }

    String removeColorFromNick(colorNick) {
        def nick = ""
        colorNick.split("\\^").each {
            if (it.length() > 0) {
                def pos = 0;
                def number = true
                while (pos < it.length() && number) {
                    try {
                        Integer.parseInt("" + it.charAt(pos))
                        pos++
                    } catch (NumberFormatException e) {
                        number = false
                    }
                }
                nick += it.substring(pos)
            }
        }
        return nick
    }

    void userInfo(id, userInfo) {
        def guid = userInfo.cl_guid
        def player = Player.findByGuid(guid)
        def nick = userInfo.name.trim()
        if (player == null) {
            player = new Player(guid:guid, ip:userInfo.ip, colorNick:nick,
                nick:removeColorFromNick(nick), urtID:id)
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
        RCon.rcon("rcon say \"^7Join: " + player.getColorNick() + ". Level: ^2" + player.getLevel() + "\"")
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
            if (player.getUser() == null) {
                RCon.rcon("rcon tell " + player.getUrtID() + " \"^7Welcome to UrTStats server. Your PIN is ^1" +
                    player.getPin() + "^7. Use it to activate your account on ^2www.urtstats.net" +
                    "^7. Use !help for help.\"")
            } else {
                RCon.rcon("rcon tell " + player.getUrtID() + " \"^7Welcome back " + player.getUser().getUsername() +
                ". Use !help for help.")
            }
            RCon.rcon("rcon tell " + player.getUrtID() + " \"" + player.getColorNick() + ". Level: ^2" + player.getLevel() + "\"")
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
                }
                if(killer.hasErrors() || !killer.save(flush:true)) {
                    log.error("Unable to persist: " + killer.dump())
                }
            } else {
                log.error("Unknown death for type: " + type)
            }
        }
    }

    void chat(id, teammessage, message) {
        println "MESSAAAGE INCOMGING! " + message
        def player = Player.findByUrtID(id)
        if (player != null) {
            def chat = new Chat(player:player, teamMessage:teammessage, message:message)
            if(chat.hasErrors() || !chat.save(flush:true)) {
                log.error("Unable to persist: " + chat.dump())
            }
            if (message.charAt(0) == '!') {
                message = message.substring(0).trim()
                int space = message.indexOf(' ')
                def cmd = ""
                if (space > 0) {
                    cmd = message.substring(0, space)
                    message = message.substring(space + 1)
                } else {
                    cmd = message
                }
                cmd = cmd.substring(1)
                println "CMD: " + cmd + ". MESSAGE: " + message
                switch (cmd) {
                    case "level":
                    case "status":
                    case "stats":
                    RCon.rcon("rcon tell ^7" + player.getUrtID() + " \"Level: " + player.getLevel() + "\"")
                    break
                    case "kick":
                    def user = player.getUser()
                    if (user != null) {
                        def permission = new JsecDbRealm().isPermitted(user.getUsername(),
                            new org.jsecurity.authz.permission.WildcardPermission("news:create"))
                        if (permission) {
                            println "KICK KICK KICK!"
                        } else {
                            println  "User without permission to !kick tried to kick"
                        }
                    } else {
                        println "Got !kick-command from non-registered user"
                    }
                    break
                }
            }
        } else {
            println "Player is null!"
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
        RCon.rcon("rcon bigtext \"UrTStats is now running! Check out ^2www.urtstats.\"")
        def players = Player.findAllByUrtIDGreaterThan(-1)
        players.each() { it.setJoinGameTime(new Date()) }
    }

    void serverStart() {
        def players = Player.findAllByUrtIDGreaterThanEquals(0)
        players.each() { leave(it.getUrtID()) }
    }

    void sync() {
        println "Here we go..."
        RCon.rcon("rcon say \"^7Server is syncing users...\"")
        def status = RCon.rcon("rcon status")

        if (status != null) {
            def reader = new BufferedReader(new StringReader(status));
            reader.readLine() // Remove print
            reader.readLine() // Remove map
            def line = reader.readLine()
            reader.readLine() //remove dots
            def map = [:]
            while ((line = reader.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(line, " ");
                if (st.countTokens() == 8) {
                    map[st.nextToken()] = [
                        score:st.nextToken(),
                        ping:st.nextToken(),
                        name:st.nextToken(),
                        lastmsg:st.nextToken(),
                        address:st.nextToken(),
                        qport:st.nextToken(),
                        rate:st.nextToken()]
                }
            }
            def max = map.size()
            def i = 0
            while (i < max) {
                line = parser.parseReverse("cl_guid", "InitRound: ")
                if (line != null) {
                    def id = line.substring(line.indexOf(":") + 1, line.indexOf('\\')).trim()
                    def userInfoString = line.substring(line.indexOf('\\'))
                    def userInfo = parser.getUserInfo(userInfoString)
                    def user = map[id]
                    if (user != null) {
                        if (user.address == userInfo.ip &&
                            user.name == userInfo.name) {
                            userInfo(userInfo)
                            i++
                        }
                    }
                } else {
                    log.error("All players was not found while syncing!")
                    break;
                }
            }
        } else {
            log.error("Got no response from RCon, setting synced to true, although it's not")
        }
        synced = true
    }
}

