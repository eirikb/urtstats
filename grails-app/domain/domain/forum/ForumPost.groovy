package domain.forum

import domain.security.JsecUser

class ForumPost {
    static belongsTo = [ForumTopic, JsecUser]

    ForumTopic topic
    JsecUser user

    String subject
    String body

    Date dateCreated
    Date lastUpdated

    static mapping = {
        body type:'text'
    }

    static constraints = {
        subject(nullable:false, blank:false)
        body(nullable:false, blank:false)
    }
}