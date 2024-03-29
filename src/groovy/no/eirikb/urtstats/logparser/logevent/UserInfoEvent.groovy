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
import no.eirikb.urtstats.utils.RCon

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class UserInfoEvent extends Event{
    def player
    
    public UserInfoEvent(line) {
        super(line)
    }

    void execute() {
        def userInfo = getUserInfo()
        if (userInfo != null) {
            player = Player.findByGuid(userInfo.cl_guid)
            def urtIDPlayer = Player.findByUrtID(getId())
            def first = true
            if (player == null) {
                player = createPlayer(userInfo)
                log.info "[UserInfoEvent] Create player: " + player
            } else  {
                updatePlayer(player, userInfo)
                first = player.getUrtID() < 0
                log.info "[UserInfoEvent] Update player: " + player
            }

            if (urtIDPlayer != null) {
                if (Player.findByGuid(urtIDPlayer.getGuid()) != player ||
                    player.getIp() != urtIDPlayer.getIp()) {
                    log.error "[UserInfoEvent] urtIDPlayer not like player! urtIDPlayer: " + urtIDPlayer +
                ". player: " + player
                }
            }

            if (first) {
                // Make sure noone in db have the id
                Player.findByUrtID(getId())?.setUrtID(-1)
                player.setJoinGameDate(new Date())
                player.gameKillCount = 0
                player.gameDeathCount = 0
                player.spreeCount = 0
                //  player.addToPlayerLogs(new PlayerLog())
                RCon.rcon("say \"^7Join: " + player.getColorNick() + ". Level: ^1" + player.getLevel() + "\"")
                RCon.rcon("tell " + getId() + "\"^7Welcome ^2" + player.getColorNick() +
                    "^7. Your level: ^2" + player.getLevel() + "^7. For help say !help\"")
            }

            //            if (addGear(player, userInfo)) {
            //                log.info "[UserInfoEvent] Items added to player: " + player
            //            } else {
            //                log.info "[UserInfoEvent] No items found for player: " + player + ". With userInfo: " + userInfo
            //            }

            try {
                if (player.getColorNick() == null) {
                    player.setColorNick("")
                }
                if(player.hasErrors() || !player.save(flush:true)) {
                    log.error "[UserInfoEvent] Unable to persist on UserInfoEvent: " + player
                } else {
                    // TeamTool.addPlayerToTeam(player, 0)
                }
            } catch(org.springframework.dao.OptimisticLockingFailureException e) {
                log.error "[UserInfoEvent] Unable to persist player - " + e.dump()
            }
        } else {
            log.warn "[UserInfoEvent] Player has no cl_guid: " + userInfo
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
                log.warn "[UserInfoEvent] Player " + player + " got his gear-string: " + gear + ". Although none was added (not found)"
                return false
            }
            return true
        }
        return false
    }

    def updatePlayer(player, userInfo) {
        player.setUrtID(getId())
        player.setIp(userInfo.ip)
        player.setColorNick(userInfo.name)
        player.setNick(PlayerTool.removeColorFromNick(userInfo.name))
        return player
    }

    def createPlayer(userInfo) {
        def player = new Player(guid:userInfo.cl_guid,
            ip:userInfo.ip,
            colorNick:userInfo.name,
            nick:PlayerTool.removeColorFromNick(userInfo.name),
            urtID:getId())
        player.addGuid()
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

