package domain.demo

import domain.urt.Player

class DemoTagRel {
    static belongsTo = [Demo, Tag]

    Demo demo
    Tag tag

    String description
    Integer startTime
    Integer endTime
    Player player

    static constraints = {
        description(nullable:true)
        startTime(nullable:true)
        endTime(nullable:true)
        player(nullable:true)
    }
}
