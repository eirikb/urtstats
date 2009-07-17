package no.eirikb.urtstats.logparser.logevent

import grails.test.*
import no.eirikb.urtstats.logparser.logevent.KillEvent
import domain.urt.Player
import domain.urt.Kill
import domain.urt.DeathCause

class KillEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetTotalRatio() {
        def killer = new Player(urtID:0, guid:"0", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        def killed = new Player(urtID:1, guid:"1", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        assertEquals 1, new KillEvent("").getTotalRatio(killer)
        new Kill(killer:killer, killed:killed, deathCause:DeathCause.findByUrtID(14), firendyfire:false).save(flush:true)
        assertEquals 2, new KillEvent("").getTotalRatio(killer)
        assertEquals 0.5, new KillEvent("").getTotalRatio(killed)
        new Kill(killer:killed, killed:killer, deathCause:DeathCause.findByUrtID(14), firendyfire:false).save(flush:true)
        assertEquals 1, new KillEvent("").getTotalRatio(killer)
        assertEquals 1, new KillEvent("").getTotalRatio(killer)
        new Kill(killer:killer, killed:killed, deathCause:DeathCause.findByUrtID(14), firendyfire:false).save(flush:true)
        new Kill(killer:killer, killed:killed, deathCause:DeathCause.findByUrtID(14), firendyfire:false).save(flush:true)
        assertEquals 2, new KillEvent("").getTotalRatio(killer)
        assertEquals 0.5, new KillEvent("").getTotalRatio(killed)
        new Kill(killer:killer, killed:killed, deathCause:DeathCause.findByUrtID(14), firendyfire:false).save(flush:true)
        assertEquals 2.5, new KillEvent("").getTotalRatio(killer)
        assertEquals 0.4, new KillEvent("").getTotalRatio(killed)
        new Kill(killer:killed, killed:killer, deathCause:DeathCause.findByUrtID(14), firendyfire:false).save(flush:true)
        assertEquals 5/3, new KillEvent("").getTotalRatio(killer)
        assertEquals 0.6, new KillEvent("").getTotalRatio(killed)
    }
}
