class JsecUser {
    static hasMany = [players:Player]
    
    String username
    String passwordHash
    String email

    static constraints = {
        username(nullable:false, blank:false, unique:true)
        email(email:true, nullable:true)
    }
}
