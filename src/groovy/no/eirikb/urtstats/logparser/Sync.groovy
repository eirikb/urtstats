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
import org.apache.commons.logging.LogFactory
import domain.urt.Player

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class Sync {
    def log
    def tail

    public Sync() {
        log = LogFactory.getLog("grails.app.task")
    }

    public Sync(tail) {
        log = LogFactory.getLog("grails.app.task")
        this.tail = tail
    }

    def sync() {
        log.info "[Sync] Start syncing..."
        def filePointer = tail.getFilePointer()
        RCon.rcon("say \"^7Syncing players...\"")
        def status = RCon.rcon("status", true)
        log.info "[Sync] Got status: " + status
        if (status != null) {
            def map = statusToMap(status)
            def max = map.size()
            def done = 0
            while (done >= 0 && done < max) {
                def line = tail.parseReverse()
                if (line.indexOf("ClientUserinfoChanged") == 0) {
                    def line2 = tail.parseReverse()
                    if (line2.indexOf("ClientUserinfo") == 0) {
                        if (addPlayer(map, line2)) {
                            new UserInfoEvent(line2).execute()
                            new UserInfoChangedEvent(line).execute()
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
                RCon.rcon("say \"^7All players were synced.\"")
            } else {
                RCon.rcon("say \"^7Not all players were synced! Check logs.\"")
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
        reader.readLine() // Remove map
        def line = reader.readLine()
        reader.readLine() //remove dots
        def map = [:]
        while ((line = reader.readLine()) != null) {
            def pos = -1
            def space = line.indexOf(' ')
            if (space >= 0) {
                def part = ""
                while (part.length() == 0) {
                    space = line.indexOf(' ')
                    part = line.substring(0, space)
                    line = line.substring(space + 1)
                }
                def mp = [:]
                map[part] = mp
                while ((space = line.indexOf(' ')) >= 0) {
                    part = line.substring(0, space)
                    line = line.substring(space + 1)
                    if (part.length() > 0) {
                        pos++
                        switch (pos) {
                            case 0:
                            mp.score = part
                            break
                            case 1:
                            mp.ping = part
                            break
                            case 2:
                            def nickEnd = part.indexOf("^7")
                            if (nickEnd >= 0) {
                                mp.name = part.substring(0, nickEnd)
                            } else {
                                nickEnd = line.indexOf("^7")
                                mp.name = part +  ' ' + line.substring(0, nickEnd)
                                line = line.substring(nickEnd + 2)
                            }
                            break
                            case 3:
                            mp.lastmsg = part
                            break
                            case 4:
                            mp.address = part
                            break
                            case 5:
                            mp.qport = part
                            break
                        }
                    }
                }
            }
        }
        return map
    }

    def resetPlayers() {
        Player.findAllByUrtIDGreaterThanEquals(0).each {
            it.setUrtID(-1)
            it.save(flush:true)
        }
    }

    def addPlayer(map, userInfoLine) {
        def uie = new UserInfoEvent(userInfoLine)
        def player = Player.findByUrtID(uie.getId())
        if (player == null) {
            def userInfo = uie.getUserInfo()
            def m = map["" + uie.getId()]
            if (m != null) {
                return true
            } else {
                log.warn "[Sync] Got player by UrtID, but did not match map! m: " + m +
                                ". userInfo: " + userInfo
            }
        }
        return false
    }
}

