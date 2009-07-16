
import domain.security.*

class AccessController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ roleList: JsecRole.list(params), roleListTotal: JsecRole.count(),
            userList: JsecUser.list (params), userListTotal: JsecUser.count() ]
    }

    def showRole = {
        def role = JsecRole.get( params.id )

        if(!role) {
            flash.message = "JsecRole not found with id ${params.id}"
            redirect(action:list)
        } else { 
            def userRels = JsecUserRoleRel.findAllByRole(role)
            def users = []
            userRels.each {
                users.add(it.user)
            }
            def permissions = JsecRolePermissionRel.findAllByRole(role)
            return [ role : role,
                userList : users, userListTotal : users.size(),
                permissionList: permissions, permissionListTotal: permissions.size()] }
    }

    def showUser = {
        def user = JsecUser.get ( params.id )
        return [user:user, roleList:JsecRole.list( params ), roleListTotal:JsecRole.count()]
    }

    def remove = {
        def user = JsecUser.get(params.id)
        println params.dump()
    }

    def addRemoveRoleTouser = {
        def user = JsecUser.get(params.userID)
        def roles = params.findAll{ param -> param.key.startsWith("role") }
        JsecUserRoleRel.findAllByUser(user).each {
            it.delete()
        }
        roles.each {
            def role = JsecRole.get(it.value)
            new JsecUserRoleRel(user:user, role:role).save()
        }
        render(view:'showUser',model:[user:user, roleList:JsecRole.list( params ), roleListTotal:JsecRole.count()])
    }

    def create = {}

    def save = {
        if (new JsecRole(params).save()) {
            flash.message = "Role created"
        } else {
            flash.message = "Could not create role..."
        }
        redirect(action:list)
    }

    def delete = {
        def role = JsecRole.get( params.id )
        if(role) {
            role.delete(flush:true)
            flash.message = "Role ${params.id} deleted"
            redirect(action:list)
        }
    }

    def addPermission = {
        def role = JsecRole.get( params.id )
        def permission = JsecPermission.findByType("org.jsecurity.grails.JsecBasicPermission")
        new JsecRolePermissionRel(role:role, permission:permission, target:params.target,
            actions:params.actions).save()
        redirect(action:showRole)
    }

    def removePermission = {
        def perm = JsecRolePermissionRel.get(params.id)
        def role = perm.getRole()
        perm.delete()
        redirect(action:showRole)
    }
}
