/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser.logevent

import no.eirikb.urtstats.utils.PlayerTool
import domain.urt.Player
import domain.urt.Team
import domain.urt.PlayerLog
import domain.urt.Item
import no.eirikb.urtstats.utils.PlayerTool
import no.eirikb.urtstats.utils.TeamTool
import no.eirikb.urtstats.utils.RCon

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
                player = updatePlayer(player, userInfo)
                log.info "Update player: " + player
            }
            player.addToPlayerLogs(new PlayerLog(startTime:new Date()))
            if (addGear(player, userInfo)) {
                log.info "Items added to player: " + player
            } else {
                log.info "No items found for player: " + player + ". With userInfo: " + userInfo
            }

            if(player.hasErrors() || !player.save(flush:true)) {
                log.error "Unable to persist on UserInfoEvent: " + player?.dump()
            } else {
                TeamTool.addPlayerToTeam(player, 0)
            }
            RCon.rcon("rcon say \"^7Join: " + player.getColorNick() + ". Level: ^2" + player.getLevel() + "\"")
        } else {
            log.warn "Player has no cl_guid: " + userInfo
        }
    }

    def addGear(player, userInfo) {
        def gear = userInfo.gear
        if (gear != null) {
            def added = false
            for (i in 0..gear.length() - 1) {
                def item = Item.findByBinding(gear.charAt(i))
                if (item != null) {
                    added = true
                    player.addToItems(item)
                }
            }
            if (!added) {
                log.warn "Player " + player + " got his gear-string: " + gear + ". Although none was added (not found)"
                return false
            }
            return true
        }
        return false
    }

    def updatePlayer(player, userInfo) {
        player.setUrtID(id)
        player.setIp(userInfo.ip)
        player.setNick(userInfo.name)
        player.setJoinGameDate(new Date())
        return player
    }

    def createPlayer(userInfo) {
        def player = new Player(guid:userInfo.cl_guid,
            ip:userInfo.ip,
            colorNick:userInfo.name,
            nick:PlayerTool.removeColorFromNick(userInfo.name),
            urtID:id)
        return player
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

