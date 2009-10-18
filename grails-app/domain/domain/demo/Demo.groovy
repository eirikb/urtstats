package domain.demo

import domain.security.JsecUser

class Demo {
    static hasMany = [tags:DemoTagRel]
    static belongsTo = [JsecUser]

    String name
    String description
    JsecUser user

    Date dateCreated
    Date lastUpdated

    static mapping = {
        description type:'text'
    }

    static constraints = {
        tags(nullable:true)
        description(nullable:true)
    }
}
