class Kill {
    static belongsTo = [Player]
    
    Player killer
    Player killed

   // Item weapon
    boolean friendlyfire
    Date dateCreated

    def beforeInsert = {
        dateCreated = new Date()
    }
        
    static constraints = {
    }
}
