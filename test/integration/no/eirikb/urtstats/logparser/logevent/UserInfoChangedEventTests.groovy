package no.eirikb.urtstats.logparser.logevent

import grails.test.*
import no.eirikb.urtstats.logparser.logevent.UserInfoChangedEvent
import domain.urt.Player
import domain.urt.Team

class UserInfoChangedEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExecute() {
        def player = new Player(guid:"1", ip:"1", nick:"1", colorNick:"1", urtID:0).save()
        player.addGuid()
        assertEquals 1, Player.count()
        assertEquals Team.findByUrtID(0), Player.findByGuid("1").getTeam()
        
        new UserInfoChangedEvent("ClientUserinfoChanged: 0 n\\1\\t\\1\\r\\2\\tl\\0\\f0\\\\f1\\\\f2\\a0\\255\\a1\\0\\a2\\0").execute()
        assertEquals 1, Team.count()
        assertEquals Team.findByUrtID(1), player.getTeam()

        new UserInfoChangedEvent("ClientUserinfoChanged: 0 n\\1\\t\\2\\r\\2\\tl\\0\\f0\\\\f1\\\\f2\\a0\\255\\a1\\0\\a2\\0").execute()
        assertEquals 2, Team.count()
        assertEquals Team.findByUrtID(2), player.getTeam()
        assertEquals 0, Team.findByUrtID(1).getPlayers().size()

        new UserInfoChangedEvent("ClientUserinfoChanged: 1 n\\1\\t\\2\\r\\2\\tl\\0\\f0\\\\f1\\\\f2\\a0\\255\\a1\\0\\a2\\0").execute()
        assertEquals 2, Team.count()
        assertEquals 1, Team.findByUrtID(2).getPlayers().size()
        assertEquals 0, Team.findByUrtID(1).getPlayers().size()
    }
}
