/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser.logevent

import domain.urt.Chat
import domain.urt.Player
import domain.security.*
import security.JsecDbRealm
import no.eirikb.urtstats.utils.RCon
import org.jsecurity.grails.JsecBasicPermission

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class ChatEvent extends Event {
    def teammessage

    public ChatEvent(line, teammessage) {
        super(line)
        this.teammessage = teammessage
    }

    void execute() {
        def id = getId()
        def player = Player.findByUrtID(id)
        if (player != null) {
            def message = line
            message = message.substring(message.indexOf(':') + 1)
            message = message.substring(message.indexOf(':') + 2)
            def chat = new Chat(player:player, teamMessage:teammessage, message:message)
            if(chat.hasErrors() || !chat.save(flush:true)) {
                log.error "[ChatEvent] Unable to persist. Chat: " + chat.dump()
            }
            log.info "[ChatEvent] Player: " + player + ". Message: " + message
            if (message.charAt(0) == '!') {
                message = message.substring(1)
                int space = message.indexOf(' ')
                def cmd = ""
                if (space > 0) {
                    cmd = message.substring(0, space)
                    message = message.substring(space + 1)
                } else {
                    cmd = message
                    message = null
                }
                command(player, cmd, message)
            }
        } else {
            log.warn "[ChatEvent] Player not found by UrtID. UrtID: " + id
        }
    }

    void command(player, cmd, message) {
        log.info "[ChatEvent] Command. Player: " + player + ". cmd: " + cmd + ". message: " + message

        switch (cmd) {
            case "help":
            RCon.rcon("tell " + player.getUrtID() + " \"^Commands: level, pin\"")
            break

            case "lvl":
            case "level":
            case "status":
            case "stats":
            def p = player
            if (message != null) {
                p = Player.findByNickIlikeAndUrtIDGreaterThanEquals('%' + message + '%', 0)
                if (p == null) {
                    p = Player.findByNickIlike('%' + message + '%')
                }
            }
            if (p != null) {
                RCon.rcon("say \"^2" + p.getColorNick() + " ^7Level: ^1" + p.getLevel() + "\"")
            } else{
                RCon.rcon("tell " + player.getUrtID() + " \"^7Player not found.\"")
            }
            break

            case "kick":
            def clos = {RCon.rcon("clientkick " + it.getUrtID())}
            rconCommand(player, cmd, message, clos)
            break

            case "pin":
            RCon.rcon("tell " + player.getUrtID() + " \"^7Your PIN is: " + player.getPin() + "\"")
            break

            case "bigtext":
            if (isPermitted(player, "bigtext")) {
                RCon.rcon("bigtext \"" + message + '"')
            }
            break

            case "slap":
            cmd = "slap"
            def clos = {RCon.rcon(cmd + ' ' + it.getUrtID())}
            rconCommand(player, cmd, message, clos)
            break

            case "forceteam":
            case "move":
            cmd = "forceteam"
            def clos = {
                if (it.getTeam().getUrtID() == 1) {
                    RCon.rcon(cmd + ' ' + it.getUrtID() + " blue")
                } else {
                    RCon.rcon(cmd + ' ' + it.getUrtID() + " red")
                }
            }
            rconCommand(player, "forceteam", message, clos)
            break

            case "fixteams":
            case "fix":
            cmd = "fixteams"
            if (isPermitted(player, "fixteams")) {
                def players = Player.findAllByUrtIDGreaterThanEquals(0)
                def red = 0
                def blue = 0
                players.each {
                    it.getTeam().getUrtID() == 1 ? red++ : blue++
                }
                if (Math.max(red, blue) - Math.min(red, blue) > 1) {
                    def moveFrom = red > blue ? 1 : 2
                    def moveTo = moveFrom == 1 ? 2 : 1
                    def movePlayers = []
                    players.each {
                        if (Math.max(red, blue) - Math.min(red, blue) > 1) {
                            if (it.getTeam().getUrtID() == move) {
                                movePlayers.add(it)
                                red > blue ? red-- : blue++
                            }
                        }
                    }
                    movePlayers.each {
                        def color = "red"
                        if (it.getTeam().getUrtID() == 2) {
                            color = blue
                        }
                        RCon.rcon("forceteam " + it.getUrtID() + ' ' + color)
                    }
                }
            }
            break

            case "say":
            RCon.rcon("say \"^7" + player.getColorNick() + ": " + message + '"')
            break
        }
    }

    def rconCommand(player, cmd, message, clos) {
        if (isPermitted(player, cmd)) {
            def found = false
            Player.findAllByNickIlikeAndUrtIDGreaterThanEquals('%' + message + '%', 0).each {
                found = true
                if (!isAdmin(it)) {
                    clos(it)
                } else {
                    RCon.rcon("slap " + player.getUrtID())
                    RCon.rcon("say \"^2" + player.getColorNick() + " ^7 tried to " + cmd + " an admin!\"")
                }
            }
            if (!found) {
                RCon.rcon("tell " + player.getUrtID() + "\"^7No players found\"")
            }

        } 
    }

    boolean isPermitted(player, permission) {
        def user = player.getUser()
        if (user != null) {
            return new JsecDbRealm().isPermitted(user.getUsername(),
                new JsecBasicPermission('urt', permission))
        } else {
            RCon.rcon("tell " + player.getUrtID() + "\"^7You are not permitted to " + permission + '"')
        }
        return false
    }

    boolean isAdmin(player) {
        return JsecUserRoleRel.findByUserAndRole(player.getUser(),
            JsecRole.findByName("ADMIN"))
    }
	
}

