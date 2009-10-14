import org.jsecurity.SecurityUtils
import domain.forum.ForumPost
import domain.forum.ReadPost
import domain.security.JsecUser

class UserForumPostReadTagLib {
    def haveUnreadPosts = { attr, body ->
        if (countPosts() > 0) {
            out << body()
        }
    }

    def countPosts()  {
        if (SecurityUtils.getSubject() != null && SecurityUtils.getSubject().getPrincipal() != null) {
            def user = JsecUser.findByUsername(SecurityUtils.getSubject().getPrincipal())
            return ForumPost.executeQuery("select count(*) from ForumPost as fp where fp.id \
            not in (select rp.post.id from ReadPost rp where rp.user=:user)", [user:user]).get(0)
        }
    }
}
