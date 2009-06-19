class Item {
    static hasMany = [hits:Hit, kills:Kill, players:Player]

    Integer urtID
    String name
    Integer mode
    String binding
    
    static constraints = {
    }
}
