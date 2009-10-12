/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser.logevent

import domain.urt.Hit
import domain.urt.Player
import domain.urt.Item

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class HitEvent extends Event {

    public HitEvent(line) {
        super(line)
    }

    void execute() {
        def ids = getIDs()
        def hitter = Player.findByUrtID(ids[2])
        def victim = Player.findByUrtID(ids[1])
        def hitpoint = ids[3]
        def item = Item.findByUrtID(ids[4])
        if (hitter != null && victim != null && item != null) {
            def hit = new Hit(hitter:hitter, victim:victim, friendlyfire:(hitter.getTeam() == victim.getTeam()),
                hitpoint:hitpoint, item:item)
            def done = false
            while (!done) {
                try {
                    if(hit.hasErrors() || !hit.save(flush:true)) {
                        log.error "[HitEvent] Unable to persist hit: " + hit
                    } else {
                        log.info "[HitEvent] Hitter:" + hitter + ". Victim: " + victim + ". Hitpoint: " + hitpoint + ". Item: " + item
                    }
                    done = true
                } catch(org.springframework.dao.OptimisticLockingFailureException e) {
                }
            }
        } else if (item == null) {
            log.error "[HitEvent] Could not create hit, unkown Item: " + item + " (" +  ids[3] + ")"
        } else {
            log.error "[HitEvent] Could not create hit. One of the following are null: hitter: " +
            hitter + ". victim: " + victim + ". item: " + item +
                ". Original(" + ids[1] + ", " + ids[2] + ", " + ids[4] + ")"
        }
    }
}

