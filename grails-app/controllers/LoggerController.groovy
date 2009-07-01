class LoggerController implements ParseListener {

    static public LogParser parser

    def index = {
        if (parser == null) {
            parser = new LogParser(new File(grailsApplication.config.urt.qconsole.path), true, true)
            parser.addParseListener(this)
            rcon("rcon bigtext \"UrTStats logging is now ^2runnin^7!\"")
            Thread.start() {
                parser.parse()
            }
            rcon("rcon bigtext \"UrTStats logging just ^1terminated^1. You kills are now not recorded\"")
        }
    }

    void userInfo(id, userInfo) {
        def guid = userInfo.cl_guid
        def player = Player.findByGuid(guid)
        if (player == null) {
            player = new Player(guid:guid, ip:userInfo.ip, nick:userInfo.name, urtID:id)
            rcon("rcon tell \"" + player.getNick() + " Welcome to UrTStats server. Your PIN is " +
            player.getPin() + ". Use it to actie your account on ^2http://urtstats.net\"")
        } else {
            player.setUrtID(id)
            player.setIp(userInfo.ip)
            player.setJoinGameTime(new Date())
            rcon("rcon tell " + player.getNick() + "\"Welcome back ^2" + player.getNick() + "^1. Your level is ^2" + player.getLevel())
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

    private void rcon(message) {
        RCon.rcon(grailsApplication.config.urt.rcon.host, grailsApplication.config.urt.rcon.port,
            grailsApplication.config.urt.rcon.password, message)
    }

    void initRound(roundInfo) {
        def players = Player.findAllByUrtIDGreaterThan(-1)
        players.each() { it.setJoinGameTime(new Date()) }
    }

    void serverStart() {
        def players = Player.findAllByUrtIDGreaterThan(-1)
        players.each() { leave(it.getUrtID()) }
    }
}
