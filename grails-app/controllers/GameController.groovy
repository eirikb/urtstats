class GameController {

    def index = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ playerInstanceList: Player.list( params ), playerInstanceTotal: Player.count() ]
    }
}
