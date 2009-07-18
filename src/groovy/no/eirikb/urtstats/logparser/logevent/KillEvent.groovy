/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

package no.eirikb.urtstats.logparser.logevent

import domain.urt.Kill
import domain.urt.Player
import domain.urt.DeathCause
import no.eirikb.urtstats.utils.RCon

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class KillEvent extends Event {
    final double NEXTLEVELMAGIC = 1.2

    public KillEvent(line) {
        super(line)
    }

    void execute() {
        def ids = getIDs()
        def killer = Player.findByUrtID(ids[1])
        def killed = Player.findByUrtID(ids[2])
        if (killer != null && killed != null) {
            def friendlyfire = killer.team == killed.team
            def death = DeathCause.findByUrtID(ids[3])
            if (death != null) {
                def kill = new Kill(killer:killer, killed:killed, friendlyfire:friendlyfire, deathCause:death)
                if(kill.hasErrors() || !kill.save(flush:true)) {
                    log.error "KillEvent: Could not persist kill: " + kill.dump()
                }
                if (!friendlyfire) {
                    killer.exp += calculateExpGain(killer, killed,
                    getGameRatio(killer), getTotalRatio(player))

                    if (killer.exp > killer.nextlevel) {
                        level(killer)
                    }
                    if(killer.hasErrors() || !killer.save(flush:true)) {
                        log.error "KillEvnent: Unale to update player after gain, player: " + killer.dump()
                    }
                }
            } else {
                log.error "KillEvent: No DeathCause for type:" + type
            }
        } else {
            log.error "KillEvent: One of the players were null. killer: " + killer + ". killed: " + killed +
            ". killerID: " + ids[1] + ". killedID: " + ids[2] + ". PlayerList: " + Player.findAllByUrtIDGreaterThanEquals(0)?.dump()
        }
    }
    
    Double getTotalRatio(player) {
        return (Kill.countByKiller(player) + 1) / (Kill.countByKilled(player) + 1)
    }
    
    Double getGameRatio(player) {
        return (Kill.countByKillerAndCreateDateGreaterThan(player, player.getJoinGameDate()) + 1) /
        (Kill.countByKilledAndCreateDateGreaterThan(player, player.getJoinGameDate()) + 1)
    }

    Integer calculateExpGain(killer, killed, gameRatio, totalRatio) {
        def levelBoost = killed.getLevel() - killer.getLevel() > 0 ? killed.getLevel() - killer.getLevel() : 1
        def ratio = ((gameRatio + totalRatio)) / 2
        return levelBoost * ratio
    }

    void level(player) {
        killer.level++;
        killer.nextlevel = killer.exp * NEXTLEVELMAGIC + Math.sqrt(killer.getExp())
        RCon.rcon("rcon bigtext \"^7Congratulations ^2" + killer.nick.trim() + "^7you are now level ^2" + killer.level + '"')
    }
}

