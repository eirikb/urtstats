/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.ircbot

import domain.urt.Player
import no.eirikb.urtstats.logparser.logevent.*
import org.jibble.pircbot.*
import no.eirikb.urtstats.utils.RCon

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class IrcBot extends PircBot {
    def name = "urtbot"
    def host = "irc.freenode.net"
    def channel = "#urtstats"

    IrcBot() {
        Event.eventListeners = [this]
    }

    def start() {
        setName(name)
        connect(host)
        joinChannel(channel)
    }

    def execute(event) {
        if (event instanceof KillEvent) {
            def ids = event.getIDs()
            def killer = Player.findByUrtID(ids[1])
            def killed = Player.findByUrtID(ids[2])
            sendMessage(channel, "$killer?.nick killed $killed?.nick")
        } else if (event instanceof ChatEvent) {
            def id = event.getId()
            def player = Player.findByUrtID(id)
            def message = event.line
            message = message.substring(message.indexOf(':') + 1)
            message = message.substring(message.indexOf(':') + 2)
            sendMessage(channel, "$player?.nick: $message")
        }
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        RCon.rcon("say\"^1$sender: $message\"")
    }
}

