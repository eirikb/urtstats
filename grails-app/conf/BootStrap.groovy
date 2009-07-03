import org.jsecurity.crypto.hash.Sha1Hash

class BootStrap {

    def init = { servletContext ->
        /*z
        def knife = new Item(urtID:1, name:"Ka-Bar Knife", mode:0, binding:"B").addToDeathCauses(
        new DeathCause(urtID:12, name:"Slice")).addToDeathCauses(
        new DeathCause(urtID:13, name:"Throw")).save()

        new Item(urtID:2, name:"Beretta", binding:"F", mode:0).addToDeathCauses(
        new DeathCause(urtID:14)).save()
        new Item(urtID:3, name:"Desert Eagle", binding:"G", mode:0).addToDeathCauses(
        new DeathCause(urtID:15)).save()
        new Item(urtID:4, name:"SPAS 12", binding:"H", mode:0).addToDeathCauses(
        new DeathCause(urtID:16)).save()
        new Item(urtID:5, name:"MP5K", binding:"I", mode:0).addToDeathCauses(
        new DeathCause(urtID:18)).save()
        new Item(urtID:6, name:"UMP45", binding:"J", mode:0).addToDeathCauses(
        new DeathCause(urtID:17)).save()
        new Item(urtID:7, name:"HK69", binding:"K", mode:0).addToDeathCauses(
        new DeathCause(urtID:22)).save()
        new Item(urtID:8, name:"300ML", binding:"L", mode:0).addToDeathCauses(
        new DeathCause(urtID:19)).save()
        new Item(urtID:9, name:"G36", binding:"M", mode:0).addToDeathCauses(
        new DeathCause(urtID:20)).save()
        new Item(urtID:10, name:"PSG-1", binding:"N", mode:0).addToDeathCauses(
        new DeathCause(urtID:21)).save()
        new Item(urtID:11, name:"High Explosive Grenade", binding:"O", mode:0).addToDeathCauses(
        new DeathCause(urtID:25)).save()
        new Item(urtID:14, name:"SR8", binding:"Z", mode:0).addToDeathCauses(
        new DeathCause(urtID:28)).save()
        new Item(urtID:15, name:"AK-103", binding:"a", mode:0).addToDeathCauses(
        new DeathCause(urtID:30)).save()
        new Item(urtID:16, name:"Negev", binding:"c", mode:0).addToDeathCauses(
        new DeathCause(urtID:35)).save()
        new Item(urtID:19, name:"M4", binding:"c", mode:0).addToDeathCauses(
        new DeathCause(urtID:38)).save()

        new Item(urtID:13, name:"Smoke Grenade", binding:"Q", mode:0).save()
        new Item(urtID:17, name:"Kevlar Vest", binding:"R", mode:0).save()
        new Item(urtID:18, name:"Kevlar Helmet", binding:"W", mode:0).save()
        new Item(urtID:19, name:"Silencer", binding:"U", mode:0).save()
        new Item(urtID:20, name:"Laser sight", binding:"V", mode:0).save()
        new Item(urtID:21, name:"Medkit", binding:"T", mode:0).save()
        new Item(urtID:22, name:"NVGs", binding:"S", mode:0).save()
        new Item(urtID:23, name:"Extra Ammo", binding:"X", mode:0).save()

        new DeathCause(urtID:6, name:"Falling").save()
        new DeathCause(urtID:24, name:"Kick").save()
        new DeathCause(urtID:23, name:"bleed").save()
        new DeathCause(urtID:31, name:"Sploded").save()
        new DeathCause(urtID:10, name:"Change team").save()


        
        def adminRole = new JsecRole(name: "ADMIN").save()
        def adminUser = new JsecUser(username: "admin", passwordHash:new Sha1Hash("admin").toHex()).save()

        new JsecUserRoleRel(user: adminUser, role: adminRole).save()

        // A normal user.
        def userRole = new JsecRole(name: "USER").save()
        def normalUser = new JsecUser(username: "phil", passwordHash: new Sha1Hash("password").toHex()).save()
        new JsecUserRoleRel(user: normalUser, role: userRole).save()

        // Give another user the "User" role.
        normalUser = new JsecUser(username: "alice", passwordHash: new Sha1Hash("changeit").toHex()).save()
        new JsecUserRoleRel(user: normalUser, role: userRole).save()



        def perm = new JsecPermission(type: 'org.jsecurity.grails.JsecBasicPermission', possibleActions: '*').save()
        new JsecUserPermissionRel(user : normalUser,  permission: perm, target : 'user', actions : 'delete, edit').save()

        //  def a = new JsecPermission(type:"Administration", possibleActions:"lol").save()
        //  new JsecRolePermissionRel(role:adminRole, permission:a, actions:"omfglol").save()
         */


        def players = Player.list()
        players.each() { it.urtID = -1}
    }

    def destroy = {
    }
} 