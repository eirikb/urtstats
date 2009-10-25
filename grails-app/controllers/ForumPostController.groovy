import domain.forum.*
import domain.security.JsecUser
import org.jsecurity.SecurityUtils

class ForumPostController {
    
    def index = { redirect(action:list,controller:forumGenre) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']


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
            def user = JsecUser.findByUsername(SecurityUtils?.getSubject()?.getPrincipal())
            println forumPostInstance.getUser()
            println user
            if (forumPostInstance.getUser() == user) {
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
                    redirect(controller:'forumTopic',action:show,id:forumPostInstance.topic?.id)
                } else {
                    flash.message = "ERROR!"
                    redirect(controller:'forumTopic',action:'show',params:[id:forumPostInstance?.getTopic()?.getId()])
                }
            } else {
                flash.message = "Not your post!"
                redirect(controller:'forumTopic',action:'show',params:[id:forumPostInstance?.getTopic()?.getId()])
            }
        }
        else {
            flash.message = "ForumPost not found with id ${params.id}"
            redirect(controller:'forumTopic',action:'list')
        }
    }

    def create = {
        def forumPost = new ForumPost()
        forumPost.properties = params
        def forumTopic = ForumTopic.get(params.id)
        def forumGenre = forumTopic.getGenre()
        return [forumPost:forumPost, forumTopic:forumTopic, forumGenre:forumGenre]
    }

    def save = {
        def user = JsecUser.findByUsername(SecurityUtils.getSubject()?.getPrincipal())
        def forumPost = new ForumPost(body:params.body)
        forumPost.setUser(user)
        def forumTopic = ForumTopic.get(params.topicID)
        def forumGenre = forumTopic.getGenre()
        forumTopic.addToPosts(forumPost)
        if(!forumPost.hasErrors() && forumPost.save()) {
            flash.message = "ForumPost ${forumPost.id} created"
            redirect(controller:"forumTopic",action:"show",id:forumTopic.getId())
        } else {
            render(view:'create',model:[forumPost:forumPost, forumTopic:forumTopic,
                    forumGenre:forumGenre])
        }
    }
}
