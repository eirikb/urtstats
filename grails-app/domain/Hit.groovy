class Hit {
    static belongsTo = [Player]

    Player hitter
    Player victim

    Item weapon
    Integer hitpoint
    Boolean friendlyfire
    Date dateCreated

    def beforeInsert = {
        dateCreated = new Date()
    }
    
    static constraints = {
    }
}
