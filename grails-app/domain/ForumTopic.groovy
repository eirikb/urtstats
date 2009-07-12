class ForumTopic {
    static hasMany = [posts:ForumPost]
    static belongsTo = [ForumGenre, JsecUser]

    ForumGenre genre
    JsecUser user

    String name

    static constraints = {
        name(nullable:false, blank:false)
    }
}
