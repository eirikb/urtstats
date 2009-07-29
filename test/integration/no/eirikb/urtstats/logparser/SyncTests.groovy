package no.eirikb.urtstats.logparser

import grails.test.*
import domain.urt.Player

class SyncTests extends GrailsUnitTestCase {
    final def STATUS = "map: ut4_riyadh\n" +
            "num score ping name            lastmsg address               qport rate\n" +
            "--- ----- ---- --------------- ------- --------------------- ----- -----\n" +
            " 1    12   91 1^7                  0 1:27960    35711  8000\n" +
            "2    13  178 2^7            0 2:27960  64877  8000\n" +
            " 3     6  184 3e^7              0 3:27960   26195  8000\n" +
            "4    11  101 E agle Eye ^7              0 4:27960   859  8000\n" +
            "    5    51  999 <[Supermarco2015]>^7   8950 5:27960    54551  8000\n" +
            " 6     0   67 ^5deagle 5      ^7        0 6:27960     244  8000\n" +
            " 7     1   94 A|crusy|BoD^7           0 7:27960   54065  8000\n\n"

    final def STATUS2 = "map: ut4_riyadh\n" +
            "num score ping name            lastmsg address               qport rate\n" +
            "--- ----- ---- --------------- ------- --------------------- ----- -----\n" +
            " 0    15   58 EagleEye^7              0 0:27960   763  8000\n" +
            " 1     0  307 Andre^7                 0 1:27960    49453  8000\n" +
            "2     5   62 dense^7                 0 2:61177       3761  8000\n" +
            " 3     1   46 bash^7                 50 3:27960     93  8000\n" +
            "  6    13  182 LOLMAN1996^7           50 6:27960     15237  8000\n" +
            "8    29   75 NexT!^7                 0 8:27960   48888  8000\n" +
            "   11     0   62 deagle 5      ^7        0 11:27960     244  8000\n\n"

    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testResetPlayers() {
        new Player(urtID:0, guid:"0", ip:"0", nick:"0", colorNick:"0").save(flush:true)
        new Player(urtID:1, guid:"1", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        new Player(urtID:-1, guid:"2", ip:"2", nick:"2", colorNick:"2").save(flush:true)
        assertEquals 3, Player.count()
        assertEquals 2, Player.countByUrtIDGreaterThanEquals(0)
        new Sync().resetPlayers()
        assertEquals 3, Player.count()
        assertEquals 0, Player.countByUrtIDGreaterThanEquals(0)
    }

    void testStatusToMap() {
        def map = new Sync().statusToMap(STATUS)
        assertEquals "1", map["1"].name
        assertEquals "1:27960", map["1"].address
        assertEquals "35711", map["1"].qport
        assertEquals "2", map["2"].name
        assertEquals "2:27960", map["2"].address
        assertEquals "3e", map["3"].name
        assertEquals "3:27960", map["3"].address
        assertEquals "E agle Eye ", map["4"].name
        assertEquals "4:27960", map["4"].address
        assertEquals "<[Supermarco2015]>", map["5"].name
        assertEquals "5:27960", map["5"].address
        assertEquals "^5deagle 5      ", map["6"].name
        assertEquals "6:27960", map["6"].address
        assertEquals "A|crusy|BoD", map["7"].name
        assertEquals "7:27960", map["7"].address

        map = new Sync().statusToMap(STATUS2)
        assertEquals "EagleEye", map["0"].name
        assertEquals "0:27960", map["0"].address
        assertEquals "Andre", map["1"].name
        assertEquals "1:27960", map["1"].address
        assertEquals "dense", map["2"].name
        assertEquals "2:61177", map["2"].address
        assertEquals "bash", map["3"].name
        assertEquals "3:27960", map["3"].address
        assertEquals "LOLMAN1996", map["6"].name
        assertEquals "6:27960", map["6"].address
        assertEquals "NexT!", map["8"].name
        assertEquals "8:27960", map["8"].address
        assertEquals "deagle 5      ", map["11"].name
        assertEquals "11:27960", map["11"].address

    }

    void testAddPlayer() {
        def map = new Sync().statusToMap(STATUS)
        def userInfoLine = "ClientUserinfo: 1 \\ip\\1:27960\\name\\1\\cl_guid\1"
        assertTrue new Sync().addPlayer(map, userInfoLine)
        userInfoLine = "ClientUserinfo: 1 \\ip\\1:27960\\name\\1\\cl_guid\1"
        assertTrue new Sync().addPlayer(map, userInfoLine)
        userInfoLine = "ClientUserinfo: 4 \\ip\\4:27960\\name\\E agle Eye \\cl_guid\1"
        assertTrue new Sync().addPlayer(map, userInfoLine)
        userInfoLine = "ClientUserinfo: 6 \\ip\\6:27960\\name\\^5deagle 5      \\cl_guid\1"
        assertTrue new Sync().addPlayer(map, userInfoLine)
        userInfoLine = "ClientUserinfo: 10 \\ip\\6:27960\\name\\^5deagle 5      \\cl_guid\1"
        assertFalse new Sync().addPlayer(map, userInfoLine)
    }
}
