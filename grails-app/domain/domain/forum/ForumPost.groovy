package domain.forum

import domain.security.JsecUser

class ForumPost {
    static hasMany = [readPost:ReadPost]
    static belongsTo = [ForumTopic, JsecUser]

    ForumTopic topic
    JsecUser user


    String body

    Date dateCreated
    Date lastUpdated

    static mapping = {
        body type:'text'
    }

    static constraints = {
        body(nullable:false, blank:false)
    }
}
