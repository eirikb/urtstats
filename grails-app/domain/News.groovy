class News {
    static belongsTo = [JsecUser]
    
    JsecUser author
    String title
    String head
    String body
    Date createTime = new Date()

    static constraints = {
        author(nullable:false)
        title(nullable:false, blank:false, size:5..255)
        head(nullable:false, blank:false)
        body(nullable:true)
    }
}
