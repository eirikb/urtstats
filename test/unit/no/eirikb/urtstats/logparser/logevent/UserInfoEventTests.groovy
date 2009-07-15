package no.eirikb.urtstats.logparser.logevent

import grails.test.*


class UserInfoEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetUserInfo() {
        def uie = new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\Save_Private_BigHead\\cl_guid\\50396332DD6331A02EB725FC88DAECE7")
        def map = uie.getUserInfo()
        assertEquals "83.225.252.223:36714", map.ip
        assertEquals "Save_Private_BigHead", map.name
        assertEquals "50396332DD6331A02EB725FC88DAECE7", map.cl_guid

        uie = new UserInfoEvent("ClientUserinfo: 0 \\ip\\83.225.252.223:36714" +
            "\\name\\Save_Private_BigHead")
        map = uie.getUserInfo()
        assertNull map
    }
}
