package no.eirikb.urtstats.logparser.logevent

import grails.test.*
import domain.urt.Player
import domain.urt.PlayerLog
import no.eirikb.urtstats.logparser.logevent.ServerEvent

class ServerEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExecute() {
        new UserInfoEvent("ClientUserinfo: 0 \\ip\\0" +"\\name\\0\\cl_guid\\0").execute()
        new UserInfoEvent("ClientUserinfo: 1 \\ip\\1" +"\\name\\1\\cl_guid\\1").execute()
        assertEquals 2, Player.count()

        def dates = [:]
        Player.list().each {
            dates.it = it.getJoinGameDate()
            assertNotNull it.getJoinGameDate()
        }

        new ServerEvent("INITROUND", "INITROUND: ....").execute()

        Player.list().each {
            assertTrue it.getJoinGameDate().getTime() > dates.it.getTime()
        }

        new ServerEvent("SERVER", "SERVER: ....").execute()

        Player.list().each {
            assertEquals  it.getUrtID(), -1
            assertNotNull PlayerLog.findByPlayerAndEndDateGreaterThan(it, new Date(new Date().getTime() - 1000))
        }
    }
}
