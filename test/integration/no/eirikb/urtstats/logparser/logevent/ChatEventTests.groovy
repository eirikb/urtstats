package no.eirikb.urtstats.logparser.logevent

import grails.test.*
import domain.urt.Chat
import domain.urt.Player
import no.eirikb.urtstats.logparser.logevent.ChatEvent
import domain.security.*
import security.JsecDbRealm
import org.jsecurity.grails.JsecBasicPermission

class ChatEventTests extends GrailsUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testExecute() {
        def player = new Player(urtID:0, guid:"0", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        assertEquals 0, Chat.count()
        new ChatEvent("sayteam: 0 test: This is a test message", true).execute()
        assertEquals 1, Chat.count()
        assertEquals "This is a test message", Chat.findByPlayer(player).getMessage()
        assertTrue Chat.findByPlayer(player).getTeamMessage()
        new ChatEvent("sayteam: 0 test: This is another test message", false).execute()
        assertEquals 2, Chat.count()
        assertEquals "This is another test message", Chat.findByTeamMessage(false).getMessage()
        assertFalse Chat.findByTeamMessage(false).getTeamMessage()
    }

    void testIsPermitted() {

        def modPlayer = new Player(urtID:0, guid:"0", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        def modUser = new JsecUser(username:"mod", passwordHash:"pass").addToPlayers(modPlayer).save(flush:true)
        new JsecUserRoleRel(user:modUser, role:JsecRole.findByName("MOD")).save(flush:true)

        def userPlayer = new Player(urtID:1, guid:"1", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        def user = new JsecUser(username:"user", passwordHash:"pass").addToPlayers(userPlayer).save(flush:true)
        new JsecUserRoleRel(user:user, role:JsecRole.findByName("USER")).save(flush:true)

        def adminPlayer = new Player(urtID:2, guid:"2", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        JsecUser.findByUsername("admin").addToPlayers(adminPlayer).save(flush:true)

        assertEquals modUser, modPlayer.getUser()
        assertEquals user, userPlayer.getUser()
        assertEquals JsecUser.findByUsername("admin"), adminPlayer.getUser()
        assertEquals 3, Player.count()
        assertEquals 3, JsecRole.count()
        assertEquals 3, JsecUser.count()
        assertEquals 1, JsecPermission.count()

        assertEquals true, new ChatEvent("", true).isPermitted(modPlayer, "kick")
        assertEquals true, new ChatEvent("", true).isPermitted(modPlayer, "slap")
        assertEquals true, new ChatEvent("", true).isPermitted(modPlayer, "bigtext")
        assertEquals true, new ChatEvent("", true).isPermitted(modPlayer, "fixteams")
        assertEquals false, new ChatEvent("", true).isPermitted(modPlayer, "asdf")

        assertEquals true, new ChatEvent("", true).isPermitted(adminPlayer, "kick")
        assertEquals true, new ChatEvent("", true).isPermitted(adminPlayer, "slap")
        assertEquals true, new ChatEvent("", true).isPermitted(adminPlayer, "bigtext")
        assertEquals true, new ChatEvent("", true).isPermitted(adminPlayer, "fixteams")
        assertEquals true, new ChatEvent("", true).isPermitted(adminPlayer, "asdf")

        assertEquals false, new ChatEvent("", true).isPermitted(userPlayer, "kick")
        assertEquals false, new ChatEvent("", true).isPermitted(userPlayer, "slap")
        assertEquals false, new ChatEvent("", true).isPermitted(userPlayer, "bigtext")
        assertEquals false, new ChatEvent("", true).isPermitted(userPlayer, "fixteams")
        assertEquals false, new ChatEvent("", true).isPermitted(userPlayer, "asdf")
    }

    void testIsAdmin() {
        def modPlayer = new Player(urtID:0, guid:"0", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        def modUser = new JsecUser(username:"mod", passwordHash:"pass").addToPlayers(modPlayer).save(flush:true)
        new JsecUserRoleRel(user:modUser, role:JsecRole.findByName("MOD")).save(flush:true)

        def userPlayer = new Player(urtID:1, guid:"1", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        def user = new JsecUser(username:"user", passwordHash:"pass").addToPlayers(userPlayer).save(flush:true)
        new JsecUserRoleRel(user:user, role:JsecRole.findByName("USER")).save(flush:true)

        def adminPlayer = new Player(urtID:2, guid:"2", ip:"1", nick:"1", colorNick:"1").save(flush:true)
        JsecUser.findByUsername("admin").addToPlayers(adminPlayer).save(flush:true)

        assertEquals false, new ChatEvent("", true).isAdmin(userPlayer)
        assertEquals false, new ChatEvent("", true).isAdmin(modPlayer)
        assertEquals true, new ChatEvent("", true).isAdmin(adminPlayer)

    }

    void testSend() {
        new Player(urtID:0, guid:"0", ip:"0", nick:"test0", colorNick:"test0").save(flush:true)
        new Player(urtID:1, guid:"1", ip:"1", nick:"test1", colorNick:"test1").save(flush:true)
        new KillEvent("Kill: 0 1 10: Test").execute()
        new KillEvent("Kill: 0 1 10: Test").execute()
        new KillEvent("Kill: 0 1 10: Test").execute()
        new KillEvent("Kill: 1 0 10: Test").execute()
        new KillEvent("Kill: 1 0 10: Test").execute()
        new ChatEvent("say: 0 test: !stats", false).execute()
    }
}
