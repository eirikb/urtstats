import org.jsecurity.authc.AuthenticationException
import org.jsecurity.authc.UsernamePasswordToken
import org.jsecurity.SecurityUtils
import org.jsecurity.crypto.hash.Sha1Hash
import domain.security.*
import domain.urt.Player

class ProfileController {

    def index = { redirect(action:edit,params:params) }

    def edit = {
        def subject = SecurityUtils.getSubject()
        if (subject.authenticated) {
            [user : JsecUser.findByUsername(subject.principal) ]
        }
    }

    def update = {
        def user = JsecUser.findByUsername(SecurityUtils.getSubject().getPrincipal())
        if(user) {
            if(params.version) {
                def version = params.version.toLong()
                if(user.version > version) {
                    user.errors.rejectValue("version", "profile.optimistic.locking.failure", "Another user has updated this Profile while you were editing.")
                    render(view:'edit',model:[user:user])
                    return
                }
            }
            user.properties = params
            if(!user.hasErrors() && user.save(flush:true)) {
                flash.message = "Profile ${user.username} updated"
                redirect(action:edit)
            }
            else {
                render(view:'edit',model:[user:user])
            }
        }
    }
}
