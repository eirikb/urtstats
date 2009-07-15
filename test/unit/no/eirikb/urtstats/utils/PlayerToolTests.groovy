package no.eirikb.urtstats.utils

import grails.test.*

class PlayerToolTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testGetUserInfo() {
        def map = PlayerTool.getUserInfo("\\name\\eirikb")
        assertEquals map.name, "eirikb"

        map = PlayerTool.getUserInfo("\\one\\1\\two\\2")
        assertEquals map.one, "1"
        assertEquals map.two, "2"

        map = PlayerTool.getUserInfo("\\one\\\\two\\2")
        assertEquals map.one, ""
        assertEquals map.two, "2"

        map = PlayerTool.getUserInfo("  \\one\\1\\two\\2\\three\\ 3")
        assertEquals map.one, "1"
        assertEquals map.two, "2"
        assertEquals map.three, " 3"

        map = PlayerTool.getUserInfo("\\name")
        assertEquals map.name, null
    }
}
