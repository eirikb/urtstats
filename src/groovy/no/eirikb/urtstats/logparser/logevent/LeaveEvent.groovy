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
import domain.urt.PlayerLog
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
            // Setting player to 'offline' is not that important
            // When a player with same URTID joins, the new player "takes over" the URTID
            if(player.hasErrors() || !player.save()) {
                log.error "[LeaveEvent] Error while updating leave for player: " + player
            } else {
                TeamTool.removePlayerFromTeam(player)
                updatePlayerLog(player)
                log.info "[LeaveEvent] Player: " + player
            }
        } else {
            log.error "[LeaveEvent] Player not found: " + id
        }
    }

    void updatePlayerLog(player) {
        def playerLog = PlayerLog.findByPlayer(player)
        if (playerLog != null) {
            playerLog.setEndDate(new Date())
            try {
                if(playerLog.hasErrors() || !playerLog.save(flush:true)) {
                    log.error "[LeaveEvent] Unable to update playerLog for player:" + player +
                    ". PlyerLog: " + playerLog
                }
            } catch(org.springframework.dao.OptimisticLockingFailureException e) {
                log.error "[LeaveEvent] Unable to persist updatePlayerLog() - " + e.dump()
            }
        } else {
            log.error "[LeaveEvent] Unable to find playerlog for player " + player
        }
    }
}

