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

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class LogParser {
    def tail
    def config
    def log
    Boolean synced

    public LogParser() {
        config = ConfigurationHolder.config
        tail = new Tail(new File(config.urt.qconsole.path), true)
        log = LogFactory.getLog("grails.app.task")
        RCon.rcon("rcon bigtext \"Test\"")
    }

    void execute() {
        println "Execute!"
    }
}

