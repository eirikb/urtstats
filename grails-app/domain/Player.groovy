class Player {
    static hasMany = [chats:Chat, hitsOther:Hit, hitsSelf:Hit, kills:Kill, deaths:Kill, playerLogs:PlayerLog, items:Item, deathCauses:DeathCause]
    static mappedBy = [hitsOther:"hitter", hitsSelf:"victim", kills:"killer", deaths:"killed"]
    static belongsTo = [Item, DeathCause, JsecUser]
    
    Team team
    JsecUser user

    Integer urtID
    String nick
    String guid
    String ip
    Integer level = 0
    Integer exp = 0
    Integer nextlevel = 5
    Date createTime = new Date()
    Date joinGameTime = new Date()
    Integer pin = 10000 + (int) (Math.random() * 10000)
    String welcomeMessage
    
    static constraints = {
        nick(nullable:false)
        user(nullable:true)
        ip(nullable:false)
        level(nullable:false)
        exp(nullable:false)
        nextlevel(nullable:false)
        guid(unique:true)
        team(nullable: true)
        items(nullable:true)
        joinGameTime(nullable:true)
        welcomeMessage(nullable:true)
    }
}
