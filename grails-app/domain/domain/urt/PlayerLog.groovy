package domain.urt

class PlayerLog {
    static belongsTo = [player:Player]

    Date createDate = new Date()
    Date endDate

    static constraints = {
        endDate(nullable:true)
    }
}
