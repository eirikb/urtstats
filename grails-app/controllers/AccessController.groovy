

class AccessController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ roleList: JsecRole.list( params ), roleListTotal: JsecRole.count(),
            userList: JsecUser.list (params ), userListTotal: JsecUser.count() ]
    }

    def showRole = {
        def role = JsecRole.get( params.id )

        if(!role) {
            flash.message = "JsecRole not found with id ${params.id}"
            redirect(action:list)
        } else { 
            def rels = JsecUserRoleRel.findAllByRole(role)
            def users = []
            rels.each {
                users.add(it.user)
            }
            return [ role : role,
                userList : users, userListTotal : users.size()] }
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
        roles.each {
            println "ROLE! " + it?.value
        }
        render(view:'showUser',model:[user:user, roleList:JsecRole.list( params ), roleListTotal:JsecRole.count()])
    }
}
