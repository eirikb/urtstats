package domain.security

import domain.urt.Player
import domain.news.News
import domain.forum.ForumPost
import domain.forum.ReadPost

class JsecUser {
    static hasMany = [players:Player, news:News, forumPosts:ForumPost, readPost:ReadPost]
    
    String username
    String passwordHash
    String email
    Boolean hideEmail = true
    Integer karma = 0
    String firstname
    String lastname

    Date dateCreated
    Date lastUpdated

    static constraints = {
        username(nullable:false, blank:false, unique:true)
        email(email:true, nullable:true)
        firstname(nullable:true)
        lastname(nullable:true)
    }

    public String toString() {
        return firstname + " " + lastname + " (" + username + ")"
    }
}
