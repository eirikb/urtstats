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

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class Event {
    def line
    def log

    public Event(line) {
        this.line = line
        log = LogFactory.getLog("grails.app.task")
    }

    void execute(){}
}

