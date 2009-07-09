import org.jsecurity.authc.AuthenticationException
import org.jsecurity.authc.UsernamePasswordToken
import org.jsecurity.SecurityUtils

class NewsController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ newsList: News.list( params ), newsInstanceTotal: News.count() ]
    }

    def show = {
        def newsInstance = News.get( params.id )

        if(!newsInstance) {
            flash.message = "News not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ newsInstance : newsInstance ] }
    }

    def delete = {
        def newsInstance = News.get( params.id )
        if(newsInstance) {
            try {
                newsInstance.delete(flush:true)
                flash.message = "News ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "News ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "News not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def newsInstance = News.get( params.id )

        if(!newsInstance) {
            flash.message = "News not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ newsInstance : newsInstance ]
        }
    }

    def update = {
        def newsInstance = News.get( params.id )
        if(newsInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(newsInstance.version > version) {
                    
                    newsInstance.errors.rejectValue("version", "news.optimistic.locking.failure", "Another user has updated this News while you were editing.")
                    render(view:'edit',model:[newsInstance:newsInstance])
                    return
                }
            }
            newsInstance.properties = params
            if(!newsInstance.hasErrors() && newsInstance.save()) {
                flash.message = "News ${params.id} updated"
                redirect(action:show,id:newsInstance.id)
            }
            else {
                render(view:'edit',model:[newsInstance:newsInstance])
            }
        }
        else {
            flash.message = "News not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def subj = SecurityUtils.subject
        if (! subj.checkPermission('news:create')) {
            // render error message, redirect, what ever...
            return
        }
        def newsInstance = new News()
        newsInstance.properties = params
        return ['newsInstance':newsInstance]
    }

    def save = {
        println params.dump()
        def news = new News(params)
        def subject = SecurityUtils.getSubject();
        if(subject.authenticated){
            JsecUser author = JsecUser.findByUsername(SecurityUtils.getSubject()?.getPrincipal());
            news.setAuthor(author)
            if(!news.hasErrors() && news.save()) {
                flash.message = "News ${news.id} created"
                redirect(action:show,id:news.id)
            } else {
                render(view:'create',model:[news:news])
            }
        } else {
            render(view:'create',model:[news:news])
        }
    }
}
