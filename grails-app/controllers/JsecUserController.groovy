

class JsecUserController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ jsecUserInstanceList: JsecUser.list( params ), jsecUserInstanceTotal: JsecUser.count() ]
    }

    def show = {
        def jsecUserInstance = JsecUser.get( params.id )

        if(!jsecUserInstance) {
            flash.message = "JsecUser not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ jsecUserInstance : jsecUserInstance ] }
    }

    def delete = {
        def jsecUserInstance = JsecUser.get( params.id )
        if(jsecUserInstance) {
            try {
                jsecUserInstance.delete(flush:true)
                flash.message = "JsecUser ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "JsecUser ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "JsecUser not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def jsecUserInstance = JsecUser.get( params.id )

        if(!jsecUserInstance) {
            flash.message = "JsecUser not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ jsecUserInstance : jsecUserInstance ]
        }
    }

    def update = {
        def jsecUserInstance = JsecUser.get( params.id )
        if(jsecUserInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(jsecUserInstance.version > version) {
                    
                    jsecUserInstance.errors.rejectValue("version", "jsecUser.optimistic.locking.failure", "Another user has updated this JsecUser while you were editing.")
                    render(view:'edit',model:[jsecUserInstance:jsecUserInstance])
                    return
                }
            }
            jsecUserInstance.properties = params
            if(!jsecUserInstance.hasErrors() && jsecUserInstance.save()) {
                flash.message = "JsecUser ${params.id} updated"
                redirect(action:show,id:jsecUserInstance.id)
            }
            else {
                render(view:'edit',model:[jsecUserInstance:jsecUserInstance])
            }
        }
        else {
            flash.message = "JsecUser not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def jsecUserInstance = new JsecUser()
        jsecUserInstance.properties = params
        return ['jsecUserInstance':jsecUserInstance]
    }

    def save = {
        def jsecUserInstance = new JsecUser(params)
        if(!jsecUserInstance.hasErrors() && jsecUserInstance.save()) {
            flash.message = "JsecUser ${jsecUserInstance.id} created"
            redirect(action:show,id:jsecUserInstance.id)
        }
        else {
            render(view:'create',model:[jsecUserInstance:jsecUserInstance])
        }
    }
}
