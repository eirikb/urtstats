class Item {
    static hasMany = [hits:Hit, kills:Kill, players:Player]

    Integer urtID
    String name
    Integer mode
    String binding


    def beforeInsert = {
        mode = 0
    }

    static constraints = {
        binding(nullalble:true, unique:true)
    }
}
