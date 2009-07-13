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

    def delete = {
        def newsInstance = News.get( params.id )
        if(newsInstance) {
            try {
                newsInstance.delete(flush:true)
                flash.message = "News ${params.id} deleted"
                redirect(controller:"home")
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "News ${params.id} could not be deleted"
                redirect(controller:"home")
            }
        }
        else {
            flash.message = "News not found with id ${params.id}"
            redirect(controller:"home")
        }
    }

    def edit = {
        def news = News.get( params.id )

        if(!news) {
            flash.message = "News not found with id ${params.id}"
            redirect(controller:"home")
        }
        else {
            return [ news : news ]
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
                redirect(controller:"home")
            }
            else {
                render(view:'edit',model:[newsInstance:newsInstance])
            }
        }
        else {
            flash.message = "News not found with id ${params.id}"
            redirect(controller:"home")
        }
    }

    def create = {
        def newsInstance = new News()
        newsInstance.properties = params
        return ['newsInstance':newsInstance]
    }

    def save = {
        def news = new News(params)
        def subject = SecurityUtils.getSubject();
        if(subject.authenticated){
            JsecUser author = JsecUser.findByUsername(SecurityUtils.getSubject()?.getPrincipal());
            news.setAuthor(author)
            if(!news.hasErrors() && news.save()) {
                flash.message = "News ${news.id} created"
            }
        }
        redirect(controller:"home")
    }
}
