import org.jsecurity.SecurityUtils
import domain.forum.ForumPost
import domain.forum.ReadPost
import domain.security.JsecUser

class ForumPostTagLibTagLib {
    def isMyPost = { attr, body ->
        println attr.dump()
        def user = JsecUser.findByUsername(SecurityUtils?.getSubject()?.getPrincipal())
        if (attr?.post?.getUser() == user) {
          //  out << body()
        }
    }
}
