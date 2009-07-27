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
import no.eirikb.urtstats.utils.TeamTool
import no.eirikb.urtstats.utils.PlayerTool

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class UserInfoChangedEvent extends Event {
    
    public UserInfoChangedEvent(line) {
        super(line)
    }

    void execute() {
        def player = Player.findByUrtID(id)
        if (player != null) {
            def userInfo = getUserInfo()
            def urtID = Integer.parseInt(userInfo.t)
            if (player.getTeam()?.getUrtID() != urtID) {
                TeamTool.addPlayerToTeam(player, urtID)
            } else if (player.getTeam() == null) {
                TeamTool.addPlayerToTeam(player, urtID)
            }
            if (player.getUser() == null) {
                RCon.rcon("tell " + getId() + "\"^7Remember to register at ^2www.urtstats.net^7 with your PIN: ^1" + player.getPin() +
                "^7Your level is ^1" + player.getLevel() + "^7.")
            }
            log.info "[UserInfoChangedEvent] Player: " + player + ". Team: " + player.getTeam().getUrtID()
        } else {
            log.error "[UserInfoChangedEvent] Unkown player: " + id + ". " + userInfo.dump()
        }
    }

    def getUserInfo() {
        def line = getLine()
        def userInfoString = line.substring(line.indexOf('\\') - 1)
        return PlayerTool.getUserInfo(userInfoString)   
    }
}

