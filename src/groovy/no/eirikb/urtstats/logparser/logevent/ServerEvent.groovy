/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser.logevent

import domain.urt.Player
import no.eirikb.urtstats.utils.RCon

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class ServerEvent extends Event {
    def cmd

    public ServerEvent(cmd, line) {
        super(line)
        this.cmd = cmd
    }

    void execute() {
        switch (cmd) {
            case "INITROUND":
            log.info "[ServerEvent] INITROUND"
            RCon.rcon("bigtext \"UrTStats is now running! Check out ^2www.urtstats.\"")
            def players = Player.findAllByUrtIDGreaterThanEquals(0)
            players.each() { it.setJoinGameDate(new Date()) }
            break

            case "SERVER":
            log.info "[ServerEvent] SERVER"
            Player.findAllByUrtIDGreaterThanEquals(0).each {
                new LeaveEvent("ClientDisconnect: " + it.getUrtID()).execute()
            }
            break
        }
    }
	
}

