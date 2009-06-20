class Player {
    static hasMany = [chats:Chat, hitters:Hit, victims:Hit, killers:Kill, killeds:Kill, playerLogs:PlayerLog, items:Item, deathCauses:DeathCause]
    static mappedBy = [hitters:"hitter", victims:"victim", killers:"killer", killeds:"killed"]
    static belongsTo = [Item, DeathCause]
    
    Team team

    Integer urtID
    String nick
    String login
    String email // TODO might remove
    Integer challenge
    String ip
    Integer level
    Integer exp
    Integer nextlevel
    Integer kills
    Integer deaths
    Date createTime
    
    static constraints = {
        nick(nullable:false)
        ip(nullable:false)
        level(nullable:false)
        exp(nullable:false)
        nextlevel(nullable:false)
        challenge(unique:true)
        team(nullable: true)
        login(nullable: true)
        email(email:true, nullable: true)
        items(nullable:true)
    }
}
