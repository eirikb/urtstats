import domain.forum.*
import domain.security.*
import org.jsecurity.SecurityUtils

class ForumTopicController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        println params.id
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ forumTopicList: ForumTopic.list( params ), forumTopicTotal: ForumTopic.count(), genreID:params.id ]
    }

    def show = {
        def forumTopic = ForumTopic.get( params.id )
        if(!forumTopic) {
            flash.message = "ForumTopic not found with id ${params.id}"
            redirect(action:list)
        } else {
            def forumGenre = forumTopic.getGenre()
            def forumPostList = ForumPost.findAllByTopic(forumTopic, [sort:"lastUpdated", order:"asc"])
            def user = JsecUser.findByUsername(SecurityUtils.getSubject().getPrincipal())
            ForumPost.findAll("from ForumPost as fp where fp.id not in (select rp.post.id \
                from ReadPost rp where rp.user=:user) AND fp.topic=:topic",
                [user: user, topic: forumTopic]).each() {
                new ReadPost(user: user, post: it).save(flush:true)
            }
            return [ forumTopic : forumTopic, forumGenre:forumGenre, forumPostList:forumPostList ]
        }
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
        def forumTopic = new ForumTopic()
        def forumPost = new ForumPost()
        def forumGenre = ForumGenre.get(params.id)
        return [forumTopic:forumTopic, forumPost:forumPost, forumGenre:forumGenre, genreID:params.id]
    }

    def save = {
        def user = JsecUser.findByUsername(SecurityUtils.getSubject()?.getPrincipal())
        def forumTopic = new ForumTopic(name:params.name)
        forumTopic.setGenre(ForumGenre.get(params.genreID))
        forumTopic.setUser(user)
        def forumPost = new ForumPost(body:params.body)
        forumTopic.addToPosts(forumPost)
        forumPost.setUser(user)
        if(!forumTopic.hasErrors() && forumTopic.save()) {
            flash.message = "ForumTopic ${forumTopic.id} created"
            redirect(action:show,id:forumTopic.id)
        } else {
            flash.error ="ForumTopic could not be created!"
            redirect(action:create,id:params.genreID)
            render(view:'create',model:[forumTopic:forumTopic, forumPost:forumPost])
        }
    }
}
