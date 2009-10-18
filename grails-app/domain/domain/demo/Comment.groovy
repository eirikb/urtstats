package domain.demo

import domain.security.JsecUser

class Comment {
    static belongsTo = [Demo, JsecUser]

    Demo demo
    JsecUser user
    String comment

    static constraints = {
    }
}
