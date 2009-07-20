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
import no.eirikb.urtstats.utils.PlayerTool
import no.eirikb.urtstats.logparser.logevent.*
import org.apache.commons.logging.LogFactory
import domain.urt.Player

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
        log.info "[Sync] Start syncing..."
        def filePointer = tail.getFilePointer()
        RCon.rcon("rcon say \"^7Syncing players...\"")
        log.info "[Sync] Setting all UrtID to -1"
        Player.findAllByUrtIDGreaterThanEquals(0).each {
            it.setUrtID(-1)
            it.save(flush:true)
            log.info "[Sync] Player UrtID set to -1. player: " + it.dump()
        }
        log.info "[Sync] All UrtID set to -1. Proof: "  + Player.countByUrtIDGreaterThanEquals(0) + " - " + Player.count()
        def status = RCon.rcon("rcon status", true)
        log.info "[Sync] Got status: " + status
        if (status != null) {
            def map = statusToMap(status)
            log.info "[Sync] Map: " + map.dump()
            def max = map.size()
            def done = 0
            while (done >= 0 && done < max) {
                def line = tail.parseReverse()
                if (line.indexOf("ClientUserinfoChanged") == 0) {
                    def line2 = tail.parseReverse()
                    if (line2.indexOf("ClientUserinfo") == 0) {
                        def online = Player.countByUrtIDGreaterThanEquals(0)
                        def player = Player.findByUrtID(PlayerTool.getId(line2))
                        if (player == null) {
                            new UserInfoEvent(line2).execute()
                            new UserInfoChangedEvent(line).execute()
                        } else {
                            log.info "[Sync] Player with UrtID already in databse. player: " + player +
                            ". line2: " + line2 + ". line: " + line
                        }
                        if (online < Player.countByUrtIDGreaterThanEquals(0))  {
                            done++
                        }
                    } else if (line2.indexOf("InitGame") == 0) {
                        done = -1
                    } else {
                        log.error "[Sync] ClientUserInfo did not come before ClientUserInfoChanged!"
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
            log.error "[Sync] Status return from RCon was null"
        }
        tail.setFilePointer(filePointer)
        log.info "[Sync] Syncing complete."
        log.info "[Sync] Players in database now:"
        Player.findByUrtIDGreaterThanEquals(0).each {
            log.info "[Sync] Guid: " + it.getGuid() + ". UrtID: " + it.getUrtID() + ". Nick: " + it.getNick() + ". IP: " + it.getIp()
        }
    }

    def statusToMap(status) {
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

