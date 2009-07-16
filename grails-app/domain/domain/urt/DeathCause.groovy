package domain.urt

class DeathCause {
    static hasMany = [kills:Kill, players:Player]

    Integer urtID
    String name
    Item item

    static constraints = {
        name(nullable:true)
        item(nullable:true)
    }
}
