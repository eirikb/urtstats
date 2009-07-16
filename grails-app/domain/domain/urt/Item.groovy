package domain.urt

class Item {
    static hasMany = [hits:Hit, kills:Kill, players:Player, deathCauses:DeathCause]
    
    Integer urtID
    String name
    Integer mode
    String binding

    static constraints = {
        urtID(unique:true)
        binding(nullalble:true)
    }
}
