package no.eirikb.urtstats.utils

import grails.test.*
import no.eirikb.urtstats.utils.TeamTool
import no.eirikb.urtstats.logparser.logevent.UserInfoEvent
import domain.urt.Team
import domain.urt.Player

class TeamToolTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testAddPlayerToTeam() {

        assertNull Team.findByUrtID(0)

        assertEquals 0, Team.count()

        new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\TeamTest\\cl_guid\\team_test" +
        "\\gear\\GZAARWX").execute()

        def player = Player.findByGuid("team_test")

        assertEquals "TeamTest", player.getNick()
        assertEquals 1, Team.count()

        Team.findByUrtID(0).getPlayers().each {
            assertEquals player, it
        }

        TeamTool.addPlayerToTeam(player, 1)
        assertEquals 2, Team.count()
        assertEquals 1, Team.findByUrtID(1).getPlayers().size()

        Team.findByUrtID(1).getPlayers().each {
            assertEquals player, it
        }

        assertEquals Team.findByUrtID(1), player.getTeam()

        TeamTool.addPlayerToTeam(player, 2)
        assertEquals 3, Team.count()
        assertEquals 0, Team.findByUrtID(1).getPlayers().size()
        assertEquals 1, Team.findByUrtID(2).getPlayers().size()

        Team.findByUrtID(2).getPlayers().each {
            assertEquals player, it
        }
        
        assertEquals Team.findByUrtID(2), player.getTeam()
    }

    void testRemovePlayerFromTeam() {
        def player = new Player(guid:"1", ip:"1", nick:"1", colorNick:"1", urtID:0).save()
        player.addGuid()
        TeamTool.addPlayerToTeam(player, 0)
        assertEquals 1, Team.count()
        assertEquals Team.findByUrtID(0), player.getTeam()

        TeamTool.removePlayerFromTeam(player)

        assertNull player.getTeam()
        assertEquals 0, Team.findByUrtID(0).getPlayers().size()

        TeamTool.removePlayerFromTeam(player)

        assertNull player.getTeam()
        assertEquals 0, Team.findByUrtID(0).getPlayers().size()

    }
}
