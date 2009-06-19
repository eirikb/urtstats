class PlayerLog {
    static belongsTo = [Player]

    Date startTime
    Date endTime
    Player player

    def beforeInsert = {
        startTime = new Date()
    }

    static constraints = {
        endTime(nullable:true)
    }
}
