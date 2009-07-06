import org.jsecurity.authc.AuthenticationException
import org.jsecurity.authc.UsernamePasswordToken
import org.jsecurity.SecurityUtils
import org.jsecurity.crypto.hash.Sha1Hash

class AuthController {
    def jsecSecurityManager
    def jcaptchaService

    def index = { redirect(action: 'login', params: params) }

    def login = {
        return [ username: params.username, rememberMe: (params.rememberMe != null), targetUri: params.targetUri ]
    }

    def signIn = {
        def authToken = new UsernamePasswordToken(params.username, params.password)

        // Support for "remember me"
        if (params.rememberMe) {
            authToken.rememberMe = true
        }

        try{
            // Perform the actual login. An AuthenticationException
            // will be thrown if the username is unrecognised or the
            // password is incorrect.
            this.jsecSecurityManager.login(authToken)

            // If a controller redirected to this page, redirect back
            // to it. Otherwise redirect to the root URI.
            def targetUri = params.targetUri ?: "/"

            log.info "Redirecting to '${targetUri}'."
            redirect(uri: targetUri)
        }
        catch (AuthenticationException ex){
            // Authentication failed, so display the appropriate message
            // on the login page.
            log.info "Authentication failure for user '${params.username}'."
            flash.message = message(code: "login.failed")

            // Keep the username and "remember me" setting so that the
            // user doesn't have to enter them again.
            def m = [ username: params.username ]
            if (params.rememberMe) {
                m['rememberMe'] = true
            }

            // Remember the target URI too.
            if (params.targetUri) {
                m['targetUri'] = params.targetUri
            }

            // Now redirect back to the login page.
            redirect(action: 'login', params: m)
        }
    }

    def signOut = {
        // Log the user out of the application.
        SecurityUtils.subject?.logout()

        // For now, redirect back to the home page.
        redirect(uri: '/')
    }

    def unauthorized = {
        render 'You do not have permission to access this page.'
    }

    def create = {}

    def save = {RegisterUserCommand cmd ->
        def captcha = jcaptchaService.validateResponse("imageCaptcha", session.id, params.captcha)
        if (!captcha) {
            flash.message = [captchaerror:"You did not enter correct captcha"]
        }
        def user = new JsecUser(username:params.username, passwordHash:new Sha1Hash(params.password).toHex())
        user.validate()
        if (!user.hasErrors() && !cmd.hasErrors()) {
            def ip = request.getRemoteAddr()
            def player = Player.findByNickIlikeAndPin(params.nick, params.pin)
            def playerIP = player.getIp()?.substring(0, player.getIp().indexOf(":"))
            if (captcha && ip == playerIP) {
                player.user = user
                def userRole = JsecRole.findByName("USER")
                if (!user.save(flush:true) || !player.save(flush:true)) {
                    log.error "Could not create user. Params(" + params.dump() + ")"
                } else {
                    new JsecUserRoleRel(user:user, role:userRole).save()
                    log.info "New user! " + user.dump()
                }
                flash.message = "Congratulations " + user.username + ". You can now log in."
                redirect(action:"login")
            } else {
                flash.error = "Your IP does not match! <a href=#IP>[Info]</a>"
            }
        }
        render (view:'create', model:[cmd:cmd, user:user])
    }
    
    def checkUsername = {
        def user = new JsecUser(username:params.value)
        user.validate()
        render(template:"checkUserTemplate", model:['user':user])
    }

    def show = {
        def subject = SecurityUtils.getSubject();
        println subject.dump()
        if (subject.authenticated) {
            createdBy = JsecUser.findByUsername(subject.principal)
            println "!!! " + subject.sump()
            println "??? " + createdBy.dump()
        }

    }

}
