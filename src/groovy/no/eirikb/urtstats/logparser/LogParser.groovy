/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser

import org.codehaus.groovy.grails.commons.*
import org.apache.commons.logging.LogFactory
import no.eirikb.utils.tail.Tail
import no.eirikb.urtstats.utils.RCon
import no.eirikb.urtstats.logparser.logevent.*
import domain.urt.Player
import domain.urt.Server

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class LogParser {
    def tail
    def config
    def log
    def parsing
    def synced

    public LogParser() {
        config = ConfigurationHolder.config
        tail = new Tail(new File(config.urt.qconsole.path), true)
        log = LogFactory.getLog("grails.app.task")
        synced = false
        parsing = false
    }

    void execute() {
        if (!parsing) {
            parsing = true
            if (synced) {
                def line
                while ((line = tail.parse()) != null) {
                    readLine(line)
                }
                if (!RCon.isActive()) {
                    RCon.setActive(true)
                }
            } else {
                RCon.rcon("bigtext \"^7" + Server.findByIdent(1)?.getWelcomeMessage() + '"')
                new Sync(tail).sync()
                synced = true
            }
            parsing = false
        }
    }

    void readLine(line) {
        def pos = line.indexOf(':')
        if (pos >= 0) {
            def cmd = line.substring(0, pos)
            switch (cmd.toUpperCase()) {
                case "CLIENTUSERINFO":
                new UserInfoEvent(line).execute()
                break
                case "CLIENTUSERINFOCHANGED":
                new UserInfoChangedEvent(line).execute()
                break
                case "CLIENTDISCONNECT":
                new LeaveEvent(line).execute()
                break
                case "KILL":
                new KillEvent(line).execute()
                break
                case "SAY":
                case "SAYTEAM":
                new ChatEvent(line, cmd == "SAYTEAM").execute()
                break
                case "HIT":
                new HitEvent(line).execute()
                break
                case "INITROUND":
                new ServerEvent(cmd, line).execute()
                break
                case "SERVER":
                new ServerEvent(cmd, line).execute()
                break

            }
        }
    }
}

