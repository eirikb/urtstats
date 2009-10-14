package domain.forum

import domain.security.JsecUser

class ReadPost {
    static belongsTo = [user:JsecUser, post:ForumPost]

    JsecUser user
    ForumPost post

    static constraints = {
    }
}
