class Hit {
    static belongsTo = [Player]

    Player hitter
    Player victim

    Integer weapon
    Integer hitpoint
    Boolean friendlyfire
    Date dateCreated

    def beforeInsert = {
        dateCreated = new Date()
    }
    
    static constraints = {
    }
}
