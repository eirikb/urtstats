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
                    log.error "[KillEvent] Could not persist kill: " + kill
                }
                if (!friendlyfire) {
                    switch (countKillStreak(killer)) {
                        case 5:
                        RCon.rcon("bigtext \"^2" + killer.getColorNick() + " ^7is on a ^1killing spree! ^7(5 in a row)\"")
                        break
                        case 10:
                        RCon.rcon("bigtext \"^2" + killer.getColorNick() + " ^7is on a ^3MADNESS spree! ^7(10 in a row)\"")
                        break
                    }


                    killer.exp += calculateExpGain(killer, killed,
                        getGameRatio(killer), getTotalRatio(killer))

                    if (killer.exp > killer.nextlevel) {
                        level(killer)
                    }
                    if (killer.hasErrors() || !killer.save(flush:true)) {
                        log.error "[KillEvnent] Unale to update player after gain, player: " + killer
                    }
                }
                log.info "[KillEvent] Killer: " + killer + ". Killed: " + killed + ". DeathCause: " + death +
                ". Killer level: " + killer.getLevel() + ". Killer exp: " + killer.getExp()
            } else {
                log.error "[KillEvent] No DeathCause for type:" + ids[3]
            }
        } else {
            log.error "[KillEvent] One of the players were null. killer: " + killer + ". killed: " + killed +
            ". killerID: " + ids[1] + ". killedID: " + ids[2] + ". PlayerList: " + Player.findAllByUrtIDGreaterThanEquals(0)
        }
    }

    int countKillStreak(player) {
        def lastDeathList = Kill.findAllByKilled(player, [max:1, sort:'createDate', order:'desc'])
        def lastDeath = lastDeathList.size() > 0 ? lastDeathList.get(0) : null
        if (lastDeath != null) {
            return Kill.countByKillerAndCreateDateGreaterThan(player, lastDeath.getCreateDate())
        } else {
            return Kill.countByKiller(player)
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
        player.level++;
        player.nextlevel = player.exp * NEXTLEVELMAGIC + Math.sqrt(player.getExp())
        RCon.rcon("bigtext \"^7Congratulations ^2" + player.nick.trim() + "^7! You are now level ^1" + player.level + '"')
    }
}

