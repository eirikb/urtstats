package no.eirikb.urtstats.logparser.logevent

import grails.test.*
import no.eirikb.urtstats.utils.PlayerTool
import no.eirikb.urtstats.logparser.logevent.UserInfoEvent
import domain.urt.Player
import domain.urt.Team

class UserInfoEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetUserInfo() {
        def uie = new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\Hello^1337 W^o^42rld\\cl_guid\\50396332DD6331A02EB725FC88DAECE7")
        def map = uie.getUserInfo()
        assertEquals "83.225.252.223:36714", map.ip
        assertEquals "Hello^1337 W^o^42rld", map.name
        assertEquals "50396332DD6331A02EB725FC88DAECE7", map.cl_guid

        uie = new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\Save_Private_BigHead")
        map = uie.getUserInfo()
        assertNull map
    }

    void testCreatePlayer() {
        def uie = new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\Hello^1337 W^o^42rld\\cl_guid\\50396332DD6331A02EB725FC88DAECE7")
        def map = uie.getUserInfo()

        def player = uie.createPlayer(map)
        assertEquals "83.225.252.223:36714", player.getIp()
        assertEquals "Hello W^orld", player.getNick()
        assertEquals "Hello^1337 W^o^42rld", player.getColorNick()
        assertEquals "50396332DD6331A02EB725FC88DAECE7", player.getGuid()
    }

    void testUpdatePlayer() {
        def uie = new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\Hello^1337 W^o^42rld\\cl_guid\\50396332DD6331A02EB725FC88DAECE7")
        def map = uie.getUserInfo()
        def player = uie.createPlayer(map)

        def map2 = [ip:"asdf", name:"test", cl_guid:"lol"]
        player = uie.updatePlayer(player, map2)
        assertEquals "asdf", player.getIp()
        assertEquals "test", player.getNick()
        assertEquals "50396332DD6331A02EB725FC88DAECE7", player.getGuid()
    }

    void testAddGear() {
        def uie = new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\Hello^1337 W^o^42rld\\cl_guid\\50396332DD6331A02EB725FC88DAECE7" +
        "\\gear\\GZAARWX")
        def map = uie.getUserInfo()
        def player = uie.createPlayer(map)
        
        assertFalse player.hasErrors()
        assertNotNull player.save(flush:true)

        assertTrue uie.addGear(player, map)

        assertTrue uie.addGear(player, [gear:"GZAA"])
        assertFalse uie.addGear(player, [gear:"AAAA"])
    }

    void testExecute() {
        new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\Hello^1337 W^o^42rld\\cl_guid\\1" +
        "\\gear\\GZAARWX").execute()
        def player = Player.findByGuid("1")
        assertNotNull player
        assertEquals "83.225.252.223:36714", player.getIp()

        new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\test\\cl_guid\\1" +
        "\\gear\\GZAARWX").execute()

        assertEquals "test", player.getNick()
        assertEquals 0, player.getUrtID()

        assertEquals 1, Player.count()

        new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\test\\cl_guid\\2" +
        "\\gear\\GZAARWX").execute()

        assertEquals 2, Player.count()

        assertEquals 2, Team.findByUrtID(0).getPlayers().size()
    }
}
