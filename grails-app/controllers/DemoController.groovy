
import domain.demo.Demo
import domain.demo.Tag
import domain.demo.DemoTagRel
import domain.security.JsecUser
import org.jsecurity.SecurityUtils
import org.codehaus.groovy.grails.commons.ConfigurationHolder

class DemoController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 5,  100)
        [ demoInstanceList: Demo.list(  sort:"lastUpdated", order:"desc", max:params.max ), demoInstanceTotal: Demo.count() ]
    }

    def delete = {
        def demoInstance = Demo.get( params.id )
        if(demoInstance) {
            try {
                demoInstance.delete(flush:true)
                flash.message = "Demo ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Demo ${params.id} could not be deleted"
                redirect(action:list)
            }
        }
        else {
            flash.message = "Demo not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def demoInstance = Demo.get( params.id )

        if(!demoInstance) {
            flash.message = "Demo not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ demoInstance : demoInstance ]
        }
    }

    def update = {
        def demoInstance = Demo.get( params.id )
        if(demoInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(demoInstance.version > version) {
                    
                    demoInstance.errors.rejectValue("version", "demo.optimistic.locking.failure", "Another user has updated this Demo while you were editing.")
                    render(view:'edit',model:[demoInstance:demoInstance])
                    return
                }
            }
            demoInstance.properties = params
            if(!demoInstance.hasErrors() && demoInstance.save()) {
                flash.message = "Demo ${params.id} updated"
                redirect(action:list)
            }
            else {
                render(view:'edit',model:[demoInstance:demoInstance])
            }
        }
        else {
            flash.message = "Demo not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def demoInstance = new Demo()
        demoInstance.properties = params
        return ['demoInstance':demoInstance]
    }

    def save = {
        def demoInstance = new Demo(params)
        demoInstance.setUser(JsecUser.findByUsername(SecurityUtils.getSubject().getPrincipal()))
        if(!demoInstance.hasErrors() && demoInstance.save(flush:true)) {
            def f = request.getFile('file')
            if(!f.empty) {
                f.transferTo( new File(ConfigurationHolder.config.demo.files.dir + demoInstance.getId() + ".flv"))
                flash.message = "Demo ${demoInstance.id} created"
                redirect(action:edit,id:demoInstance.id)
            }
            else {
                flash.message = 'file cannot be empty'
                render(view:'create',model:[demoInstance:demoInstance])
            }
        }
        else {
            render(view:'create',model:[demoInstance:demoInstance])
        }
    }

    def getFile = {
        def file = new File(ConfigurationHolder.config.demo.files.dir + params.id)
        response.setContentType("application/octet-stream")
        response.setHeader("Content-disposition", "attachment;filename=${file.getName()}")
        response.outputStream << file.newInputStream()
    }
}
