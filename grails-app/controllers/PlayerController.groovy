import domain.urt.Player
import domain.urt.Hit

class PlayerController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def search= {
        def players = Player.findAllByNickIlike("%${params.query}%")
        render(contentType: "text/xml") {
	    results() {
	        players.each { player ->
		    result(){
		        name(player.nick)
                        id(player.id)
		    }
		}
            }
        }
    }

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        params.offset = params.offset ? params.offset : 0
        def sort = params.sort ? "p." + params.sort : "p.nick"
        if (params.sort == "headshots") {
            sort = "COUNT(h)"
        }
        def order = params.order ? params.order : "asc"
        def players = Player.executeQuery("SELECT new map(p.id as id, p.nick as nick, \
            p.level as level, p.exp as exp, p.nextlevel as nextlevel, p.kills.size as kills, \
            COUNT(h) as headshots) \
            FROM Player p LEFT JOIN p.hitsOther h WITH h.hitpoint = 0 \
            GROUP BY p.id, p.nick, p.level, p.exp, p.nextlevel ORDER BY " + sort + " " + order,
            [max:params.max.toInteger(), offset:params.offset.toInteger()])
        [ playerList: players, playerTotal: Player.count() ]
    }

    def show = {
        def player = Player.get( params.id )
        println player.dump()
        if(!player) {
            flash.message = "Player not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ player:player ] }
    }

    def delete = {
        def playerInstance = Player.get( params.id )
        if(playerInstance) {
            try {
                playerInstance.delete(flush:true)
                flash.message = "Player ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Player ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Player not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def playerInstance = Player.get( params.id )

        if(!playerInstance) {
            flash.message = "Player not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ playerInstance : playerInstance ]
        }
    }

    def update = {
        def playerInstance = Player.get( params.id )
        if(playerInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(playerInstance.version > version) {
                    
                    playerInstance.errors.rejectValue("version", "player.optimistic.locking.failure", "Another user has updated this Player while you were editing.")
                    render(view:'edit',model:[playerInstance:playerInstance])
                    return
                }
            }
            playerInstance.properties = params
            if(!playerInstance.hasErrors() && playerInstance.save()) {
                flash.message = "Player ${params.id} updated"
                redirect(action:show,id:playerInstance.id)
            }
            else {
                render(view:'edit',model:[playerInstance:playerInstance])
            }
        }
        else {
            flash.message = "Player not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def save = {
        def playerInstance = new Player(params)
        if(!playerInstance.hasErrors() && playerInstance.save()) {
            flash.message = "Player ${playerInstance.id} created"
            redirect(action:show,id:playerInstance.id)
        }
        else {
            render(view:'create',model:[playerInstance:playerInstance])
        }
    }
}
