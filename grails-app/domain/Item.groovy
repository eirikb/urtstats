class Item {
    static hasMany = [hits:Hit, kills:Kill, players:Player, deathCauses:DeathCause]
    
    Integer urtID
    String name
    Integer mode
    String binding

    static constraints = {
        binding(nullalble:true)
    }
}
