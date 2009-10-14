import domain.forum.*
import org.jsecurity.SecurityUtils
import domain.security.JsecUser

class ForumGenreController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        def list = ForumGenre.executeQuery("select forumGenre.id, forumGenre.name, \
             count(topics) from ForumGenre forumGenre \
            join forumGenre.topics topics group by forumGenre.name, forumGenre.id")
        def forumPostList
        if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().getPrincipal() != null) {
            def user = JsecUser.findByUsername(SecurityUtils.getSubject().getPrincipal())
            forumPostList = ForumPost.findAll("from ForumPost as fp where fp.id not in \
            (select rp.post.id from ReadPost rp where rp.user=:user)", [user:user])
        }
        [forumGenreList: list, forumGenreTotal:list.count(),
            forumPostList: forumPostList]
    }

    def show = {
        def forumGenre = ForumGenre.get( params.id )

        if(!forumGenre) {
            flash.message = "ForumGenre not found with id ${params.id}"
            redirect(action:list)
        } else {
            def forumTopicList = ForumTopic.executeQuery("select forumTopic.id, forumTopic.name, forumTopic.user.username, count(posts) \
            from ForumTopic forumTopic \
            join forumTopic.posts posts where forumTopic.genre = ? \
            group by forumTopic.id, forumTopic.name, forumTopic.user.username", [forumGenre])
            return [ forumGenre : forumGenre, forumTopicList:forumTopicList,
                forumTopicTotal:forumTopicList.size() ]
        }
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
