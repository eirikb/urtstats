/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.utils

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
}

