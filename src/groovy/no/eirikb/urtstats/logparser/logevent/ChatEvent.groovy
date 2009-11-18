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
import domain.urt.Kill
import domain.urt.Hit
import domain.urt.InfoMessage
import domain.security.*
import security.JsecDbRealm
import no.eirikb.urtstats.utils.RCon
import no.eirikb.urtstats.utils.PlayerTool
import java.text.DecimalFormat
import no.eirikb.utils.translate.Translate
import org.jsecurity.grails.JsecBasicPermission

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class ChatEvent extends Event {
    def teammessage
    def message
    def translated
    def player

    public ChatEvent(line, teammessage) {
        super(line)
        this.teammessage = teammessage
    }

    void execute() {
        def id = getId()
        player = Player.findByUrtID(id)
        if (player != null) {
            message = line
            message = message.substring(message.indexOf(':') + 1)
            message = message.substring(message.indexOf(':') + 2)
            def chat = new Chat(player:player, teamMessage:teammessage, message:message)
            //Saving of chat messages does not need to be flushed
            if(chat.hasErrors() || !chat.save()) {
                log.error "[ChatEvent] Unable to persist. Chat: " + chat
            }
            log.info "[ChatEvent] Player: " + player + ". Message: " + message
            if (message.charAt(0) == '!') {
                def orig = message
                message = message.substring(1)
                int space = message.indexOf(' ')
                def cmd = ""
                if (space > 0) {
                    cmd = message.substring(0, space)
                    message = message.substring(space + 1)
                } else {
                    cmd = message.toLowerCase()
                    message = null
                }
                command(player, cmd, message)
                message = orig
            } else if (message.split(" ")?.length > 2) {
                //                translated = Translate.translate(message)
                //                if (translated != message) {
                //                    RCon.rcon("say \"^7(trans) " + player.nick?.trim() + ": " + translated + '"')
                //                }
            }
            super.execute()
        } else {
            log.warn "[ChatEvent] Player not found by UrtID. UrtID: " + id
        }
    }

    void command(player, cmd, message) {
        log.info "[ChatEvent] Command. Player: " + player + ". cmd: " + cmd + ". message: " + message
        switch (cmd) {
            case "help":
            case "h":
            if (message == null) {
                RCon.rcon("tell " + player.getUrtID() + " \"^7Oh hai there. Use !help command to get more info on command. Check out !about\"")
                RCon.rcon("tell " + player.getUrtID() + " \"^7Commands: level, pin, stats, say\"")
            } else {
                switch (message) {
                    case "level":
                    RCon.rcon("tell " + player.getUrtID() + " \"^7Level is the level you get by killing other players. This can not decrement. Aliases: lvl\"")
                    break
                    case "stats":
                    RCon.rcon("tell " + player.getUrtID() + " \"^7Status will show more information about your player, like level and ratio. Aliases: status, stat, s\"")
                    break
                    case "pin":
                    RCon.rcon("tell " + player.getUrtID() + " \"^7Pin is the pin-code you need to register on urtstats.net\"")
                    break
                    case "say":
                    RCon.rcon("tell " + player.getUrtID() + " \"^7Say is a simple command you can use if you want to talk while in spec\"")
                    break
                }
            }
            break

            case "about":
            RCon.rcon("tell " + player.getUrtID() + " \"^1EagleEye^7 (eirikb) - eirikb@urtstats.net made this shit!\"")
            break

            case "lvl":
            case "level":
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

            case "status":
            case "stats":
            case "stat":
            case "s":
            def p = player
            if (message != null) {
                p = Player.findByNickIlikeAndUrtIDGreaterThanEquals('%' + message + '%', 0)
                if (p == null) {
                    p = Player.findByNickIlike('%' + message + '%')
                }
            }
            if (p != null) {
                def text = "say \"^2" + player.getColorNick() +
                    " ^7Level: ^1" + player.getLevel() +
                    " ^7kills: ^1"  + player.killCount +
                    " ^7Ratio: ^1"  + new DecimalFormat("#,###.##").format((player.killCount + 1) / (player.deathCount + 1)) +
                    " ^7NextLevel in: ^1" + (player.getNextlevel() - player.getExp()) +
                    '"'
                RCon.rcon(text)
            } else{
                RCon.rcon("tell " + player.getUrtID() + " \"^7Player not found.\"")
            }
            break

            case "lol":
            if (isAdmin(player)) {
                RCon.rcon("bigtext \"^2HAHAHA ^3HAAAA ^4HAHA HA ^6THAT IS ^5SOOO ^2FUNNEH!\"")
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
            if (message != null) {
                cmd = "slap"
                def space = message.indexOf(' ')
                def times = 1
                if (space > 0) {
                    try {
                        times = Integer.parseInt(message.substring(space + 1))
                    } catch (NumberFormatException e) {
                        times = 1
                    }
                    message = message.substring(0, space)
                }
                def clos = {RCon.rcon(cmd + ' ' + it.getUrtID())}
                for (i in 1..times) {
                    rconCommand(player, cmd, message, clos)
                }
            }
            break

            case "forceteam":
            case "move":
            cmd = "forceteam"
            def clos = {
                if (it.teamID == 1) {
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

            case "set":
            if (isPermitted(player, "infomessage")) {
                def space = message.indexOf(' ')
                def cmd2 = message
                if (space > 0)  {
                    cmd2 = message.substring(0, space)
                    message = message.substring(space + 1)
                } else {
                    message = null
                }
                createInfoMessage(cmd2, message, false)
            }
            break

            case "settell":
            if (isPermitted(player, "infomessage")) {
                def space = message.indexOf(' ')
                def cmd2 = message
                if (space > 0)  {
                    cmd2 = message.substring(0, space)
                    message = message.substring(space + 1)
                } else {
                    message = null
                }
                createInfoMessage(cmd2, message, true)
            }
            break

            default:
            def infoMessage = InfoMessage.findByCommand(cmd)
            if (infoMessage != null) {
                if (!infoMessage.getTell()) {
                    def s = ""
                    if (message == null) {
                        def toNick = Player.findByNickIlikeAndUrtIDGreaterThanEquals('%' + message + '%', 0)?.getNick()
                        s = toNick != null ? toNick + ": " : ""
                    }
                    RCon.rcon("say \"^7" + s + infoMessage.getInfoMessage() + '"')
                } else {
                    RCon.rcon("tell " + player.getUrtID() + "\"^7" + s + infoMessage.getInfoMessage() + '"')
                }
            }
            break
        }
    }

    def createInfoMessage(cmd, message, tell) {
        def infoMessage = InfoMessage.findByCommand(cmd)
        if (message != null) {
            if (infoMessage == null) {
                new InfoMessage(command: cmd, infoMessage: message, tell: tell).save(flush:true)
            } else {
                infoMessage.setMessage(message)
                infoMessage.save(flush:true)
            }
            RCon.rcon("tell " + player.getUrtID() + " \"^7Message with command " + cmd + " created! Use '!set " + cmd +"' to delete the message.\"")
        } else {
            if (infoMessage != null) {
                infoMessage.delete(flush:true)
                RCon.rcon("tell " + player.getUrtID() + " \"^7Message deleted\"")
            } else {
                RCon.rcon("tell " + player.getUrtID() + " \"^7You must specify a message\"")
            }
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

