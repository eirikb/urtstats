class GameController {

    def index = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        def players = Player.findAllByUrtIDGreaterThan(-1)
        [ playerInstanceList: players, playerInstanceTotal: players.count() ]
    }
}
