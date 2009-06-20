class LoggerController implements ParseListener {

    static public LogParser parser

    def index = {
        if (parser == null) {
            println "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1  " + grailsApplication.config.foo.bar.hello
            parser = new LogParser(new File("/home/eirikb/qconsole.log"), false, false)
            parser.addParseListener(this)
            parser.parse()
            //TODO THREAD!
            //   Thread.start() {
            //      parser.parse()
            // }
        }
    }

    void userInfo(id, userInfo) {
        def challenge = Integer.parseInt(userInfo.challenge)
        def player = Player.findByChallenge(challenge)
        if (player == null) {
            player = new Player(challenge:challenge, ip:userInfo.ip, nick:userInfo.name, 
                level:0, exp:0, nextlevel:10, urtID:id, kills:0, deaths:0, createTime:new Date())
        } else {
            player.setUrtID(id)
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
        def urtID = Integer.parseInt(userInfo.t)
        def same = player.team.urtID == urtID
        if (player.team.urtID != urtID) {
            addPlayerToTeam(player, urtID)
        }
    }

    void leave(id) {
        def player = Player.findByUrtID(id)
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
    }

    void kill(killerID, killedID, type) {
        def killer = Player.findByUrtID(killerID)
        def killed = Player.findByUrtID(killedID)
        if (killer != null && killed != null) {
            def friendlyfire = killer.team == killed.team
            def death = DeathCause.findByUrtID(type)
            if (death != null) {
                def kill = new Kill(killer:killer, killed:killed, friendlyfire:friendlyfire, deathCause:death, createTime:new Date())
                if(kill.hasErrors() || !kill.save(flush:true)) {
                    log.error("Unable to persist: " + kill.dump())
                }

                if (!friendlyfire) {
                    def kills = Kill.countBykiller(killer)
                    def deaths = Kill.countByKilled(killed)
                    def incExp = (int)((killer.level + 1) * ((kills + 1) / (deaths + 1)))
                    killer.exp += incExp
                    if (killer.exp > killer.nextlevel) {
                        killer.level++;
                        killer.nextlevel = killer.exp * killer.level
                        //  RCon.rcon("rcon bigtext \"Congratulations " + killer.nick.trim() + " you are now level " + killer.level + '"')
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
        def chat = new Chat(player:player, teamMessage:teammessage, message:message, createTime:new Date())
        if(chat.hasErrors() || !chat.save(flush:true)) {
            log.error("Unable to persist: " + chat.dump())
        }
    }

    void hit(hitterID, victimID, hitpoint, weapon) {
        def hitter = Player.findByUrtID(hitterID)
        def victim = Player.findByUrtID(victimID)
        def item = Item.findByUrtID(weapon)
        if (hitter != null && victim != null && item != null) {
            def hit = new Hit(hitter:hitter, victim:victim, friendlyfire:(hitter.team == victim.team), createTime:new Date(),
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
}
