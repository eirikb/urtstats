class ForumPost {
    static belongsTo = [ForumTopic, JsecUser]

    ForumTopic topic
    JsecUser user

    String subject
    String body

    static mapping = {
        body type:'text'
    }

    static constraints = {
        subject(nullable:false, blank:false)
        body(nullable:false, blank:false)
    }
}
