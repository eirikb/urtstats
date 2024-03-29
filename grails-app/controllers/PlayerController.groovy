import domain.urt.Player
import domain.urt.Hit
import domain.urt.Kill

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
        def players
        def infoMap = [:]
        if (params.sort != "kills") {
            players = Player.list ( params )
            players.each {
                infoMap.(it.getId()) = [:]
                infoMap.(it.getId()).kills = Kill.countByKiller(it)
            }
        } else {
            def order = params.order != null ? params.order : "desc";
            players = Player.executeQuery("select p.id, p.nick, p.ip, p.level, p.nextlevel from Player as p left join p.kills as k group by p.id, p.nick, p.ip, p.level, p.nextlevel order by count(k) " + order)
        }
        [ players: players, infoMap:infoMap, playerTotal: Player.count() ]
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
        playerInstance.addGuid()
        if(!playerInstance.hasErrors() && playerInstance.save()) {
            flash.message = "Player ${playerInstance.id} created"
            redirect(action:show,id:playerInstance.id)
        }
        else {
            render(view:'create',model:[playerInstance:playerInstance])
        }
    }
}
