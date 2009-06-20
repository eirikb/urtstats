class PlayerLog {
    static belongsTo = [player:Player]

    Date startTime
    Date endTime

    static constraints = {
        endTime(nullable:true)
    }
}
