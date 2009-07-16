package domain.urt

class Team {
    static hasMany = [players:Player]
    
    Integer urtID

    static constraints = {
    }
}
