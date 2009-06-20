class Kill {
    static belongsTo = [Player]
    
    Player killer
    Player killed

    DeathCause deathCause
    boolean friendlyfire
    Date createTime
        
    static constraints = {
    }
}
