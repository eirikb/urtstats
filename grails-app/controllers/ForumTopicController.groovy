

class ForumTopicController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ forumTopicInstanceList: ForumTopic.list( params ), forumTopicInstanceTotal: ForumTopic.count() ]
    }

    def show = {
        def forumTopicInstance = ForumTopic.get( params.id )

        if(!forumTopicInstance) {
            flash.message = "ForumTopic not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ forumTopicInstance : forumTopicInstance ] }
    }

    def delete = {
        def forumTopicInstance = ForumTopic.get( params.id )
        if(forumTopicInstance) {
            try {
                forumTopicInstance.delete(flush:true)
                flash.message = "ForumTopic ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "ForumTopic ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "ForumTopic not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def forumTopicInstance = ForumTopic.get( params.id )

        if(!forumTopicInstance) {
            flash.message = "ForumTopic not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ forumTopicInstance : forumTopicInstance ]
        }
    }

    def update = {
        def forumTopicInstance = ForumTopic.get( params.id )
        if(forumTopicInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(forumTopicInstance.version > version) {
                    
                    forumTopicInstance.errors.rejectValue("version", "forumTopic.optimistic.locking.failure", "Another user has updated this ForumTopic while you were editing.")
                    render(view:'edit',model:[forumTopicInstance:forumTopicInstance])
                    return
                }
            }
            forumTopicInstance.properties = params
            if(!forumTopicInstance.hasErrors() && forumTopicInstance.save()) {
                flash.message = "ForumTopic ${params.id} updated"
                redirect(action:show,id:forumTopicInstance.id)
            }
            else {
                render(view:'edit',model:[forumTopicInstance:forumTopicInstance])
            }
        }
        else {
            flash.message = "ForumTopic not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def forumTopicInstance = new ForumTopic()
        forumTopicInstance.properties = params
        return ['forumTopicInstance':forumTopicInstance]
    }

    def save = {
        def forumTopicInstance = new ForumTopic(params)
        if(!forumTopicInstance.hasErrors() && forumTopicInstance.save()) {
            flash.message = "ForumTopic ${forumTopicInstance.id} created"
            redirect(action:show,id:forumTopicInstance.id)
        }
        else {
            render(view:'create',model:[forumTopicInstance:forumTopicInstance])
        }
    }
}
