import domain.forum.*

class ForumGenreController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        [forumGenreList: ForumGenre.list(), forumGenreTotal:ForumGenre.count()]
    }

    //*********************************************

    def show = {
        def forumGenreInstance = ForumGenre.get( params.id )

        if(!forumGenreInstance) {
            flash.message = "ForumGenre not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ forumGenreInstance : forumGenreInstance ] }
    }

    def delete = {
        def forumGenreInstance = ForumGenre.get( params.id )
        if(forumGenreInstance) {
            try {
                forumGenreInstance.delete(flush:true)
                flash.message = "ForumGenre ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "ForumGenre ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "ForumGenre not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def forumGenreInstance = ForumGenre.get( params.id )

        if(!forumGenreInstance) {
            flash.message = "ForumGenre not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ forumGenreInstance : forumGenreInstance ]
        }
    }

    def update = {
        def forumGenreInstance = ForumGenre.get( params.id )
        if(forumGenreInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(forumGenreInstance.version > version) {
                    
                    forumGenreInstance.errors.rejectValue("version", "forumGenre.optimistic.locking.failure", "Another user has updated this ForumGenre while you were editing.")
                    render(view:'edit',model:[forumGenreInstance:forumGenreInstance])
                    return
                }
            }
            forumGenreInstance.properties = params
            if(!forumGenreInstance.hasErrors() && forumGenreInstance.save()) {
                flash.message = "ForumGenre ${params.id} updated"
                redirect(action:show,id:forumGenreInstance.id)
            }
            else {
                render(view:'edit',model:[forumGenreInstance:forumGenreInstance])
            }
        }
        else {
            flash.message = "ForumGenre not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def forumGenreInstance = new ForumGenre()
        forumGenreInstance.properties = params
        return ['forumGenreInstance':forumGenreInstance]
    }

    def save = {
        def forumGenreInstance = new ForumGenre(params)
        if(!forumGenreInstance.hasErrors() && forumGenreInstance.save()) {
            flash.message = "ForumGenre ${forumGenreInstance.id} created"
            redirect(action:show,id:forumGenreInstance.id)
        }
        else {
            render(view:'create',model:[forumGenreInstance:forumGenreInstance])
        }
    }
}
