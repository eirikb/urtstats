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
            if (event.didLevel) {
                sendMessage(channel, "    " + c(5, "LEVEL: ") + event.killer?.nick?.trim() + " is now level " + c(3, event.killer?.level))
            }
            if (event.spree != null) {
                sendMessage(channel, "    " + c(5, "SPREE: ") + event.killer?.nick?.trim() + " is on killing spree (" + c(2, event.spree?.end) + ") " + event.kills + " kills")
            }
        } else if (event instanceof ChatEvent) {
            sendMessage(channel, event.player?.nick?.trim() + ": " + event.message)
            if (event.translated != null && event.message != event.translated) {
                sendMessage(channel, "(trans) " + event.player?.nick?.trim() + ": " + event.translated)
            }
        }
    }

    def c(code, text) {
        char c = 3;
        return  "$c" + new String("" + code) + text + c
    }

    public void onMessage(String channel, String sender, String login, String hostname, String message) {
        RCon.rcon("say\"^7$sender: $message\"")
    }
}

