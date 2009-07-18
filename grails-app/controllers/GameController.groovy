import domain.urt.Player

class GameController {

    def index = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        def players = Player.findAllByUrtIDGreaterThan(-1, params)
        [ playerInstanceList: players, playerInstanceTotal: players.count() ]
    }
}
