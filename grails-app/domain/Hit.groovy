class Hit {
    static belongsTo = [Player]

    Player hitter
    Player victim

    Item item
    Integer hitpoint
    Boolean friendlyfire
    Date createTime
    
    static constraints = {
    }
}
