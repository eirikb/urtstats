class Kill {
    static belongsTo = [Player]
    
    Player killer
    Player killed

    Integer weapon
    boolean friendlyfire
    Date dateCreated

    def beforeInsert = {
        dateCreated = new Date()
    }
        
    static constraints = {
    }
}
