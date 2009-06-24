

class JsecUserRoleRelController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ jsecUserRoleRelInstanceList: JsecUserRoleRel.list( params ), jsecUserRoleRelInstanceTotal: JsecUserRoleRel.count() ]
    }

    def show = {
        def jsecUserRoleRelInstance = JsecUserRoleRel.get( params.id )

        if(!jsecUserRoleRelInstance) {
            flash.message = "JsecUserRoleRel not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ jsecUserRoleRelInstance : jsecUserRoleRelInstance ] }
    }

    def delete = {
        def jsecUserRoleRelInstance = JsecUserRoleRel.get( params.id )
        if(jsecUserRoleRelInstance) {
            try {
                jsecUserRoleRelInstance.delete(flush:true)
                flash.message = "JsecUserRoleRel ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "JsecUserRoleRel ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "JsecUserRoleRel not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def jsecUserRoleRelInstance = JsecUserRoleRel.get( params.id )

        if(!jsecUserRoleRelInstance) {
            flash.message = "JsecUserRoleRel not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ jsecUserRoleRelInstance : jsecUserRoleRelInstance ]
        }
    }

    def update = {
        def jsecUserRoleRelInstance = JsecUserRoleRel.get( params.id )
        if(jsecUserRoleRelInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(jsecUserRoleRelInstance.version > version) {
                    
                    jsecUserRoleRelInstance.errors.rejectValue("version", "jsecUserRoleRel.optimistic.locking.failure", "Another user has updated this JsecUserRoleRel while you were editing.")
                    render(view:'edit',model:[jsecUserRoleRelInstance:jsecUserRoleRelInstance])
                    return
                }
            }
            jsecUserRoleRelInstance.properties = params
            if(!jsecUserRoleRelInstance.hasErrors() && jsecUserRoleRelInstance.save()) {
                flash.message = "JsecUserRoleRel ${params.id} updated"
                redirect(action:show,id:jsecUserRoleRelInstance.id)
            }
            else {
                render(view:'edit',model:[jsecUserRoleRelInstance:jsecUserRoleRelInstance])
            }
        }
        else {
            flash.message = "JsecUserRoleRel not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def jsecUserRoleRelInstance = new JsecUserRoleRel()
        jsecUserRoleRelInstance.properties = params
        return ['jsecUserRoleRelInstance':jsecUserRoleRelInstance]
    }

    def save = {
        def jsecUserRoleRelInstance = new JsecUserRoleRel(params)
        if(!jsecUserRoleRelInstance.hasErrors() && jsecUserRoleRelInstance.save()) {
            flash.message = "JsecUserRoleRel ${jsecUserRoleRelInstance.id} created"
            redirect(action:show,id:jsecUserRoleRelInstance.id)
        }
        else {
            render(view:'create',model:[jsecUserRoleRelInstance:jsecUserRoleRelInstance])
        }
    }
}
