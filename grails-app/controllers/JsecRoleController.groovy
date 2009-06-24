

class JsecRoleController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ jsecRoleInstanceList: JsecRole.list( params ), jsecRoleInstanceTotal: JsecRole.count() ]
    }

    def show = {
        def jsecRoleInstance = JsecRole.get( params.id )

        if(!jsecRoleInstance) {
            flash.message = "JsecRole not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ jsecRoleInstance : jsecRoleInstance ] }
    }

    def delete = {
        def jsecRoleInstance = JsecRole.get( params.id )
        if(jsecRoleInstance) {
            try {
                jsecRoleInstance.delete(flush:true)
                flash.message = "JsecRole ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "JsecRole ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "JsecRole not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def jsecRoleInstance = JsecRole.get( params.id )

        if(!jsecRoleInstance) {
            flash.message = "JsecRole not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ jsecRoleInstance : jsecRoleInstance ]
        }
    }

    def update = {
        def jsecRoleInstance = JsecRole.get( params.id )
        if(jsecRoleInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(jsecRoleInstance.version > version) {
                    
                    jsecRoleInstance.errors.rejectValue("version", "jsecRole.optimistic.locking.failure", "Another user has updated this JsecRole while you were editing.")
                    render(view:'edit',model:[jsecRoleInstance:jsecRoleInstance])
                    return
                }
            }
            jsecRoleInstance.properties = params
            if(!jsecRoleInstance.hasErrors() && jsecRoleInstance.save()) {
                flash.message = "JsecRole ${params.id} updated"
                redirect(action:show,id:jsecRoleInstance.id)
            }
            else {
                render(view:'edit',model:[jsecRoleInstance:jsecRoleInstance])
            }
        }
        else {
            flash.message = "JsecRole not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def jsecRoleInstance = new JsecRole()
        jsecRoleInstance.properties = params
        return ['jsecRoleInstance':jsecRoleInstance]
    }

    def save = {
        def jsecRoleInstance = new JsecRole(params)
        if(!jsecRoleInstance.hasErrors() && jsecRoleInstance.save()) {
            flash.message = "JsecRole ${jsecRoleInstance.id} created"
            redirect(action:show,id:jsecRoleInstance.id)
        }
        else {
            render(view:'create',model:[jsecRoleInstance:jsecRoleInstance])
        }
    }
}
