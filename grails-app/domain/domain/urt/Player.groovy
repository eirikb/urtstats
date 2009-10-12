package domain.urt

import domain.security.JsecUser

class Player {
    static hasMany = [chats:Chat, hitsOther:Hit, hitsSelf:Hit, kills:Kill, deaths:Kill, playerLogs:PlayerLog, items:Item, deathCauses:DeathCause, guids:GUID]
    static mappedBy = [hitsOther:"hitter", hitsSelf:"victim", kills:"killer", deaths:"killed"]
    static belongsTo = [Item, DeathCause, JsecUser]
    
    Team team
    JsecUser user

    Integer urtID
    String nick
    String colorNick
    String guid
    String ip
    Integer level = 0
    Integer exp = 0
    Integer nextlevel = 5
    Date joinGameDate = new Date()
    Integer pin = 1000 + (int) (Math.random() * 1000)
    String welcomeMessage

    Date dateCreated
    Date lastUpdated

    static Player findByGuid(String guid) {
        return GUID.findByGuid(guid)?.getPlayer()
    }

    void addGuid() {
        println "addGuid()"
        addGuid(guid)
    }

    void addGuid(guid) {
        println "addGuid(guid) - " + guid
        println "  player: " + this
        this.addToGuids(new GUID(guid:guid)).save(flush:true)
    }

    static constraints = {
        nick(nullable:false)
        colorNick(nullable:false)
        user(nullable:true)
        ip(nullable:false)
        level(nullable:false)
        exp(nullable:false)
        nextlevel(nullable:false)
        guid(unique:true)
        team(nullable: true)
        items(nullable:true)
        joinGameDate(nullable:true)
        welcomeMessage(nullable:true)
    }
}
