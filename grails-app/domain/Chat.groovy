class Chat {
    static belongsTo = Player

    Player player

    String message
    Boolean teamMessage
    Date dateCreated

    def beforeInsert = {
        dateCreated = new Date()
    }
    
    static constraints = {
    }
}
