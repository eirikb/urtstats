package domain.urt

class GUID {

    static belongsTo = {Player}

    String guid
    Player player

    static constraints = {
        guid(nullable:false, unique:true)
        player(nullable:false)
    }
}
