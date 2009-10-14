/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.utils

import domain.urt.Player
import domain.urt.Team
import org.apache.commons.logging.LogFactory

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class TeamTool {

    public static void addPlayerToTeam(player, teamID) {
        def log = LogFactory.getLog("grails.app.task")

        def team = Team.findByUrtID(teamID)
        if (team == null) {
            // This should just work
            team = new Team(urtID:teamID)
            if(team.hasErrors() || !team.save(flush:true)) {
                log.error "Unable to create team (teamID: " + teamID +"). Team: " + team
            }
        }
        player.getTeam()?.removeFromPlayers(player)
        if (player.getTeam() == null || player.getTeam() != team) {
            team.addToPlayers(player)
            try {
                if(team.hasErrors() || !team.save(flush:true)) {
                    log.error "Unable to add player to team. Player: "  + player+
                    ". Team: " + team
                }
            } catch(org.springframework.dao.OptimisticLockingFailureException e) {
                log.error "[TeamTool] Unable to persist team when adding players - " + e.dump()
            } catch (org.springframework.dao.DataIntegrityViolationException ex) {

            }
        }

    }

    public static void removePlayerFromTeam(player) {
        def log = LogFactory.getLog("grails.app.task")

        def team = player.getTeam()
        if (team != null) {
            team.removeFromPlayers(player)
            try {
                if(team.hasErrors() || !team.save(flush:true)) {
                    log.error "Unable to remove player from team. Player: "  + player +
                    ". Team: " + team
                }
            } catch(org.springframework.dao.OptimisticLockingFailureException e) {
                log.error "[TeamTool] Unable to persist team when removing players - " + e.dump()
            }
        } else {
            log.warn "RemovePlayerFromTeam: Could not remove, as player had no team. Player: " + player
        }
    }
}

