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
        def done = false
        while (!done) {
            try {
                if (team == null) {
                    team = new Team(urtID:teamID)
                    if(team.hasErrors() || !team.save(flush:true)) {
                        log.error "Unable to create team (teamID: " + teamID +"). Team: " + team
                    }
                }
                done = true
            } catch(org.springframework.dao.OptimisticLockingFailureException e) {
            }
        }
        player.getTeam()?.removeFromPlayers(player)
        if (player.getTeam() == null || player.getTeam() != team) {
            team.addToPlayers(player)
            while (!done) {
                try {
                    if(team.hasErrors() || !team.save(flush:true)) {
                        log.error "Unable to add player to team. Player: "  + player+
                    ". Team: " + team
                    }
                    done = true
                } catch(org.springframework.dao.OptimisticLockingFailureException e) {
                }
            }
        }

    }

    public static void removePlayerFromTeam(player) {
        def log = LogFactory.getLog("grails.app.task")

        def team = player.getTeam()
        if (team != null) {
            team.removeFromPlayers(player)
            def done = false
            while (!done) {
                try {
                    if(team.hasErrors() || !team.save(flush:true)) {
                        log.error "Unable to remove player from team. Player: "  + player +
                    ". Team: " + team
                    }
                    done = true
                } catch(org.springframework.dao.OptimisticLockingFailureException e) {
                }
            }
        } else {
            log.warn "RemovePlayerFromTeam: Could not remove, as player had no team. Player: " + player
        }
    }
}

