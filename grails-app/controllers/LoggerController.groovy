class LoggerController implements ParseListener {

    static public LogParser parser

    def index = {
        if (parser == null) {
            parser = new LogParser(new File("/home/eirikb/qconsole3.log"), false, false)
            parser.addParseListener(this)
            parser.parse()
            //TODO THREAD!
            //   Thread.start() {
            //      parser.parse()
            // }
        }
    }

    void userInfo(id, userInfo) {
        long time = System.nanoTime()
        def challenge = Integer.parseInt(userInfo.challenge)
        def player = Player.findByChallenge(challenge)
        if (player == null) {
            player = new Player(challenge:challenge, ip:userInfo.ip, nick:userInfo.name, 
                level:0, exp:0, nextlevel:10, urtPlayerID:id, kills:0, deaths:0)
        } else {
            player.setUrtPlayerID(id)
        }
        // player.addToPlayerLogs(new PlayerLog(player:player))
        if(player.hasErrors() || !player.save(flush:true)) {
            println "******************** EEROORRRR"
            println player.dump()
        } else {
            addPlayerToTeam(player, 0)
        }

        time = System.nanoTime() - time;
        //   println time + " userinfo"
    }

    private void addPlayerToTeam(player, teamID) {
        long time = System.nanoTime()
        def team = Team.findByUrtTeamID(teamID)
        if (team == null) {
            team = new Team(urtTeamID:teamID)
            if(team.hasErrors() || !team.save(flush:true)) {
                println "******************** EEROORRRR"
                println team.dump()
            }
        } 
        if (player.team == null || player.team != team) {
            player.setTeam(team)
            if(team.hasErrors() || !team.save(flush:true)) {
                println "******************** EEROORRRR"
                println team.dump()
            }
        }
        time = System.nanoTime() - time;
        // println time + " add player to team"
    }

    void userInfoChange(id, userInfo) {
        long time = System.nanoTime()
        def player = Player.findByUrtPlayerID(id)
        def urtTeamID = Integer.parseInt(userInfo.t)
        def same = player.team.urtTeamID == urtTeamID
        if (player.team.urtTeamID != urtTeamID) {
            addPlayerToTeam(player, urtTeamID)
        }
        time = System.nanoTime() - time;
        // println time + " userinfochange"
    }

    void leave(id) {
        long time = System.nanoTime()
        def player = Player.findByUrtPlayerID(id)
        player.team = null;
        player.urtPlayerID = -1;
        if(player.hasErrors() || !player.save(flush:true)) {
            println "******************** EEROORRRR"
            println player.dump()
        }
        //def playerLog = player.playerLogs.findByEndDateIsNull()
        def playerLog = null
        if (playerLog != null) {
            playerLog.setEndDate(new Date())
            if(playerLog.hasErrors() || !playerLog.save(flush:true)) {
                println "******************** EEROORRRR"
                println playerLog.dump()
            }
        } else {
            println "FFFFFFFFFFFUUUUUUUUUUUUUU"
        }
        time = System.nanoTime() - time;
        //println time + " leave"
    }

    void kill(killerID, killedID, type) {
        long time = System.nanoTime()
        def killer = Player.findByUrtPlayerID(killerID)
        def killed = Player.findByUrtPlayerID(killedID)
        if (killer != null && killed != null) {
            def friendlyfire = killer.team == killed.team
            def kill = new Kill(killer:killer, killed:killed, friendlyfire:friendlyfire, weapon:type)
            if(kill.hasErrors() || !kill.save(flush:true)) {
                println "******************** EEROORRRR"
                println kill.dump()
            }

            if (!friendlyfire) {
                def kills = Kill.countBykiller(killer)
                def deaths = Kill.countByKilled(killed)
                def incExp = (int)((killer.level + 1) * (kills / deaths))
                killer.exp += incExp
                if (killer.exp > killer.nextlevel) {
                    killer.level++;
                    killer.nextlevel = killer.exp * killer.level
                    //  RCon.rcon("rcon bigtext \"Congratulations " + killer.nick.trim() + " you are now level " + killer.level + '"')
                }
                if(killer.hasErrors() || !killer.save(flush:true)) {
                    println "******************** EEROORRRR"
                    println killer.dump()
                }
            }
        }
        time = System.nanoTime() - time;
        //println time + " kill"
    }

    void chat(id, teammessage, message) {
        long time = System.nanoTime()
        def player = Player.findByUrtPlayerID(id)
        def chat = new Chat(player:player, teamMessage:teammessage, message:message)
        if(chat.hasErrors() || !chat.save(flush:true)) {
            println "******************** EEROORRRR"
            println chat.dump()
        }
        time = System.nanoTime() - time;
        // println time + " chat"
    }

    void hit(hitterID, victimID, hitpoint, weapon) {
        long time = System.nanoTime()
        def hitter = Player.findByUrtPlayerID(hitterID)
        def victim = Player.findByUrtPlayerID(victimID)
        if (hitter != null && victim != null) {
            def hit = new Hit(hitter:hitter, victim:victim, friendlyfire:(hitter.team == victim.team),
                hitpoint:hitpoint, weapon:weapon)
            if(hit.hasErrors() || !hit.save(flush:true)) {
                println "******************** EEROORRRR"
                println hit.dump()
            }
        }
        time = System.nanoTime() - time;
        // println time + " hit"
    }
}
