/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser

import no.eirikb.urtstats.utils.RCon
import no.eirikb.urtstats.logparser.logevent.*

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class Sync {
    def log
    def tail

    public Sync(tail) {
        log = LogFactory.getLog("grails.app.task")
        this.tail = tail
    }

    def sync() {
        log.info "Start syncing..."
        def filePointer = tail.getFilePointer()
        RCon.rcon("rcon say \"^7Syncing players...\"")
        def status = RCon.rcon("rcon status", true)
        if (status != null) {
            map = statusToMap(status)
            def max = map.size()
            def done = 0
            while (done >= 0 && done < max) {
                def line = tail.readReverse()
                if (line.indexOf("ClientUserinfoChanged") == 0) {
                    def line2 = tail.readReverse()
                    if (line2.indexOf("ClientUserinfo") == 0) {
                        new UserInfoEvent(line2).execute()
                        new UserInfoChangedEvent(line).execute()
                        done++
                    } else if (line2.indexOf("InitGame") == 0) {
                        done = -1
                    } else {
                        log.error "Sync: ClientUserInfo did not come before ClientUserInfoChanged!"
                    }
                } else if (line.indexOf("InitGame") == 0) {
                    done = -1
                }
            }
            if (done == max) {
                RCon.rcon("rcon say \"^7All users were synced.\"")
            } else {
                RCon.rcon("rcon say \"^7Not all players were synced! Check logs.\"")
            }
        } else {
            log.error "Sync: status return from RCon was null"
        }
        tail.setFilePointer(filePointer)
    }

    def statusToMap(stats) {
        def reader = new BufferedReader(new StringReader(status));
        reader.readLine() // Remove print
        reader.readLine() // Remove map
        def line = reader.readLine()
        reader.readLine() //remove dots
        def map = [:]
        while ((line = reader.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, " ");
            if (st.countTokens() == 8) {
                map[st.nextToken()] = [
                    score:st.nextToken(),
                    ping:st.nextToken(),
                    name:st.nextToken(),
                    lastmsg:st.nextToken(),
                    address:st.nextToken(),
                    qport:st.nextToken(),
                    rate:st.nextToken()]
            }
        }
        return map
    }
}

