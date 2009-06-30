/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
interface ParseListener {
    void userInfo(id, userInfo)
    void userInfoChange(id, userInfo)
    void leave(id)
    void kill(killerID, killedID, type)
    void chat(id, teammessage, message)
    void hit(hitterID, victimID, hitpoint, weapon)
    void initRound(roundInfo)
    void serverStart()
}

