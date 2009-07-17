/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser.logevent

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class UserInfoChangedEvent extends Event {
    public UserInfoChangedEvent(line) {
        super(line)
    }

    void execute() {
        /*
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
            RCon.rcon("rcon tell " + player.getUrtID() + " \"^7" + player.getColorNick() + ". Level: ^2" + player.getLevel() + "\"")
        } else {
            log.error("Unkown player: " + id + ". " + userInfo.dump())
        }
        */
    }
}

