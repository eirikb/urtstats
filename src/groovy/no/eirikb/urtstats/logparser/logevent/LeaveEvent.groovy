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
import no.eirikb.urtstats.utils.TeamTool

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class LeaveEvent extends Event {

    public LeaveEvent(line) {
        super(line)
    }

    void execute() {
        def player = Player.findByUrtID(id)
        if (player != null) {
            player.urtID = -1;
            if(player.hasErrors() || !player.save(flush:true)) {
                log.error "Error while updating leave for player: " + player.dump()
            } else {
                TeamTool.removePlayerFromTeam(player)
            }
        } else {
            log.error "LeaveEvent: Player not found: " + id
        }
    }

    void updatePlayerLog() {
        def playerLog = PlayerLog.findByPlayer(player)
        if (playerLog != null) {
            playerLog.setEndTime(new Date())
            if(playerLog.hasErrors() || !playerLog.save(flush:true)) {
                log.error "LeaveEvent: Unable to update playerLog for player:" + player.dump() +
                    ". PlyerLog: " + playerLog
            }
        } else {
            log.error "LeaveEvent: Unable to find playerlog for player " + player.dump()
        }
    }
}

