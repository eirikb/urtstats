class Player {
    static hasMany = [chats:Chat, hitters:Hit, victims:Hit, killers:Kill, killeds:Kill, playerLogs:PlayerLog, items:Item, deathCauses:DeathCause]
    static mappedBy = [hitters:"hitter", victims:"victim", killers:"killer", killeds:"killed"]
    static belongsTo = [Item, DeathCause]
    
    Team team

    Integer urtID
    String nick
    String login
    String email // TODO might remove
    String guid
    String ip
    Integer level = 0
    Integer exp = 0
    Integer nextlevel = 5
    Integer kills = 0
    Integer deaths = 0
    Date createTime = new Date()
    Date joinGameTime = new Date()
    
    static constraints = {
        nick(nullable:false)
        ip(nullable:false)
        level(nullable:false)
        exp(nullable:false)
        nextlevel(nullable:false)
        guid(unique:true)
        team(nullable: true)
        login(nullable: true)
        email(email:true, nullable: true)
        items(nullable:true)
        joinGameTime(nullable:true)
    }
}
