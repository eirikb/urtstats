/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser.logevent

import org.codehaus.groovy.grails.commons.*
import no.eirikb.urtstats.utils.PlayerTool

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class UserInfoEvent extends Event{
    def player
    def id
    
    public UserInfoEvent(line) {
        super(line)
        id = PlayerTool.getId(line)
    }

    void execute() {
        def userInfo = getUserInfo()

        if (userInfo != null) {
            player = Player.findByGuid(userInfo.cl_guid)
            if (player == null) {
                player = createPlayer(userInfo)
                log.info "Create player: " + player
            } else {
                updatePlayer(userInfo)
                log.info "Update player: " + player
            }

            player.addToPlayerLogs(new PlayerLog(startTime:new Date()))

            if (addGear(player)) {
                log.info "Items added to player: " + player
            } else {
                log.info "No items found for player: " + player + ". With userInfo: " + userInfo
            }

            if(player.hasErrors() || !player.save(flush:true)) {
                log.error "Unable to persist: " + player.dump()
            } else {
                //addPlayerToTeam(player, 0)
            }

            RCon.rcon("rcon say \"^7Join: " + player.getColorNick() + ". Level: ^2" + player.getLevel() + "\"")

        } else {
            log.warn "Player has no cl_guid: " + userInfo
        }
    }

    def addGear(userInfo) {
        def gear = userInfo.gear
        if (gear != null) {
            for (i in 0..gear.length() - 1) {
                def item = gear.charAt(i)
                if (item != null) {
                    player.addToItems(item)
                }
            }
            return true
        }
        return false
    }

    def updatePlayer(userInfo) {
        player.setUrtID(id)
        player.setIp(userInfo.ip)
        player.setNick(userInfo.name)
        player.setJoinGameDate(new Date())
    }

    def createPlayer(userInfo) {
        return new Player(guid:guid, ip:userInfo.ip, colorNick:nick,
            nick:removeColorFromNick(nick), urtID:id)
    }

    def getUserInfo() {
        def line = getLine()
        if (line.indexOf("cl_guid") > 0) {
            def userInfoString = line.substring(line.indexOf('\\'))
            return PlayerTool.getUserInfo(userInfoString)
        } else {
            return null
        }
    }


}

