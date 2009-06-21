

class TeamController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ teamInstanceList: Team.list( params ), teamInstanceTotal: Team.count() ]
    }

    def show = {
        def teamInstance = Team.get( params.id )

        if(!teamInstance) {
            flash.message = "Team not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ teamInstance : teamInstance ] }
    }

    def delete = {
        def teamInstance = Team.get( params.id )
        if(teamInstance) {
            try {
                teamInstance.delete(flush:true)
                flash.message = "Team ${params.id} deleted"
                redirect(action:list)
            }
            catch(org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "Team ${params.id} could not be deleted"
                redirect(action:show,id:params.id)
            }
        }
        else {
            flash.message = "Team not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def teamInstance = Team.get( params.id )

        if(!teamInstance) {
            flash.message = "Team not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ teamInstance : teamInstance ]
        }
    }

    def update = {
        def teamInstance = Team.get( params.id )
        if(teamInstance) {
            if(params.version) {
                def version = params.version.toLong()
                if(teamInstance.version > version) {
                    
                    teamInstance.errors.rejectValue("version", "team.optimistic.locking.failure", "Another user has updated this Team while you were editing.")
                    render(view:'edit',model:[teamInstance:teamInstance])
                    return
                }
            }
            teamInstance.properties = params
            if(!teamInstance.hasErrors() && teamInstance.save()) {
                flash.message = "Team ${params.id} updated"
                redirect(action:show,id:teamInstance.id)
            }
            else {
                render(view:'edit',model:[teamInstance:teamInstance])
            }
        }
        else {
            flash.message = "Team not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def create = {
        def teamInstance = new Team()
        teamInstance.properties = params
        return ['teamInstance':teamInstance]
    }

    def save = {
        def teamInstance = new Team(params)
        if(!teamInstance.hasErrors() && teamInstance.save()) {
            flash.message = "Team ${teamInstance.id} created"
            redirect(action:show,id:teamInstance.id)
        }
        else {
            render(view:'create',model:[teamInstance:teamInstance])
        }
    }
}
