package domain.demo

import domain.urt.Player

class DemoTagRel {
    static belongsTo = [Demo, Tag]

    Demo demo
    Tag tag

    String description
    int start
    int end
    Player player

    static constraints = {
        description(nullable:true)
        start(nullable:true)
        end(nullable:true)
        player(nullable:true)
    }
}