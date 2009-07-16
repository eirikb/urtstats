import domain.forum.*

class ForumPostController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ forumPostInstanceList: ForumPost.list( params ), forumPostInstanceTotal: ForumPost.count() ]
    }

    def show = {
        def forumPostInstance = ForumPost.get( params.id )

        if(!forumPostInstance) {
            flash.message = "ForumPost not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ forumPostInstance : forumPostInstance ] }
    }

    def delete = {
        def forumPostInstance = ForumPost.get( params.id )
        if(forumPostInstance) {
            try {
                forumPostInstance.delete(flush:true)
                flash.message = "ForumPost ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "ForumPost ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "ForumPost not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def forumPostInstance = ForumPost.get( params.id )

        if(!forumPostInstance) {
            flash.message = "ForumPost not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ forumPostInstance : forumPostInstance ]
        }
    }

    def update = {
        def forumPostInstance = ForumPost.get( params.id )
        if(forumPostInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(forumPostInstance.version > version) {
                    
                    forumPostInstance.errors.rejectValue("version", "forumPost.optimistic.locking.failure", "Another user has updated this ForumPost while you were editing.")
                    render(view:'edit',model:[forumPostInstance:forumPostInstance])
                    return
                }
            }
            forumPostInstance.properties = params
            if(!forumPostInstance.hasErrors() && forumPostInstance.save()) {
                flash.message = "ForumPost ${params.id} updated"
                redirect(action:show,id:forumPostInstance.id)
            }
            else {
                render(view:'edit',model:[forumPostInstance:forumPostInstance])
            }
        }
        else {
            flash.message = "ForumPost not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def forumPostInstance = new ForumPost()
        forumPostInstance.properties = params
        return ['forumPostInstance':forumPostInstance]
    }

    def save = {
        def forumPostInstance = new ForumPost(params)
        if(!forumPostInstance.hasErrors() && forumPostInstance.save()) {
            flash.message = "ForumPost ${forumPostInstance.id} created"
            redirect(action:show,id:forumPostInstance.id)
        }
        else {
            render(view:'create',model:[forumPostInstance:forumPostInstance])
        }
    }
}
