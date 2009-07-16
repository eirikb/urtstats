package domain.forum

class ForumGenre {
    static hasMany = [topics:ForumTopic]

    String name

    static constraints = {
        name(nullable:false, blank:false)
    }
}
