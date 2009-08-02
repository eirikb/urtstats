package no.eirikb.urtstats.logparser.logevent

import grails.test.*
import domain.urt.Hit
import domain.urt.Player
import domain.urt.Item
import no.eirikb.urtstats.logparser.logevent.HitEvent

class HitEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExecute() {
        def hitter = new Player(urtID:0, guid:"0", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        def victim = new Player(urtID:1, guid:"1", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        assertEquals 2, Player.count()

        new HitEvent("Hit: 1 0 4 9: Test...").execute()

        assertEquals 1, Hit.count()
        assertEquals 1, Hit.countByHitter(hitter)
        assertEquals 1, Hit.countByVictim(victim)
        assertEquals 1, Hit.countByHitpoint(4)
        assertEquals 1, Hit.countByItem(Item.findByUrtID(9))
    }
}
