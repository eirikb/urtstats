/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.utils

import domain.urt.Kill
import domain.urt.Player

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class PlayerTool {
    
    public static Map getUserInfo(userInfoString) {
        userInfoString = userInfoString.trim()
        if (userInfoString.charAt(0) == '\\') {
            userInfoString = userInfoString.substring(1)
        }
        def userInfo = [:]
        def st = userInfoString.split("\\\\")
        def i = 0
        while (i < st.length) {
            def s = st[i++]
            if (i < st.length) {
                def s2 = st[i++]
                userInfo.put(s, s2);
            }
        }
        return userInfo;
    }

    public static Integer getId(line) {
        line = line.substring(line.indexOf(':') + 1).trim()
        def space = line.indexOf(' ')
        if (space >= 0) {
            line = line.substring(0, space)
        }
        try {
            return Integer.parseInt(line.trim())
        } catch (Exception e) {}
        return null
    }

    public static String removeColorFromNick(colorNick) {
        def nick = ""
        if (colorNick != null) {
            colorNick.split("\\^\\d+").each {
                nick += it
            }
        } else {
            return ""
        }
        return nick
    }


    static int countKillStreak(player) {
        return player.spreeCount
    }

    static Double getTotalRatio(player) {
        return ((player.killCount + 1) / (player.deathCount + 1))
    }

    static Double getGameRatio(player) {
        return ((player.gameKillCount + 1) / (player.gameDeathCount + 1)) 
    }
}

