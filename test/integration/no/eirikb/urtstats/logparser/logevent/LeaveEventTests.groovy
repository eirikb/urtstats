package no.eirikb.urtstats.logparser.logevent

import grails.test.*
import no.eirikb.urtstats.logparser.logevent.LeaveEvent
import domain.urt.Player
import domain.urt.Team
import domain.urt.PlayerLog
import no.eirikb.urtstats.utils.TeamTool

class LeaveEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExecute() {

        def uie = new UserInfoEvent("ClientUserinfo: 1 \\ip\\83.225.252.223:36714" +
            "\\name\\test\\cl_guid\\test").execute()
        assertEquals 1, Player.count()
        assertEquals 1, Team.count()
        assertEquals 1, PlayerLog.count()
        
        def player = Player.findByGuid("test")
        def playerLogDate = PlayerLog.findByPlayer(player)
        def joinGameDate = player.getJoinGameDate()
        new LeaveEvent("ClientDisconnect: 1").execute()
        assertTrue PlayerLog.findByPlayer(player).getEndDate().getTime() - new Date().getTime() < 1000
      

        player = new Player(guid:"1", ip:"1", nick:"1", colorNick:"1", urtID:0).save(flush:true)
        TeamTool.addPlayerToTeam(player, 0)
        assertEquals 1, Team.count()
        assertEquals Team.findByUrtID(0), player.getTeam()

        new LeaveEvent("ClientDisconnect: 0").execute()
        assertNull player.getTeam()
        assertEquals 0, Team.findByUrtID(0).getPlayers().size()

        uie = new UserInfoEvent("ClientUserinfo: 1 \\ip\\83.225.252.223:36714" +
            "\\name\\test\\cl_guid\\test").execute()
        assertFalse playerLogDate == PlayerLog.findByPlayer(player)
        assertFalse playerLogDate == player.getJoinGameDate()
    }
}
