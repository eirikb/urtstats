/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser.logevent

import org.apache.commons.logging.LogFactory
import no.eirikb.urtstats.utils.PlayerTool

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class Event {
    def line
    def log
    def id
    def static eventListeners

    public Event(line) {
        this.line = line
        log = LogFactory.getLog("grails.app.task")
        eventListeners.each { it.execute(this) }
    }

    void execute(){}

    def getId() {
        return id == null ?  id = PlayerTool.getId(line) : id
    }



    def getIDs() {
        def ids = []
        def st = line.substring(line.indexOf(':') + 1).split(" ")
        st.each {
            def i = it.indexOf(':')
            if (i < 0) {
                ids.add(it)
            } else {
                ids.add(it.substring(0, i))
            }
        }
        return ids
    }
}

