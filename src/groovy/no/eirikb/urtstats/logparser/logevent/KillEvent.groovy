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
import no.eirikb.urtstats.utils.PlayerTool

/**
 *
 * @author Eirik Brandtzæg eirikdb@gmail.com
 */
class KillEvent extends Event {
    final double NEXTLEVELMAGIC = 1.2
    def killer
    def killed
    def didLevel
    def spree
    def kills
    def spreeMessage = [
        5:[text:"is on a ^1killing spree!", end:"killing spree"],
        10:[text:"is on a ^3MADNESS spree!", end:"MADNESS spree"],
        15:[text:"is UNSTOPPABLE!", end:"UNSTOPPABLE run"]
    ]


    public KillEvent(line) {
        super(line)
    }

    void execute() {
        def ids = getIDs()
        didLevel = false
        killer = Player.findByUrtID(ids[1])
        killed = Player.findByUrtID(ids[2])
        if (killer != null && killed != null) {
            // def friendlyfire = killer.team == killed.team
            def friendlyfire = false
            def death = DeathCause.findByUrtID(ids[3])
            if (death != null) {
                //def spreeEnd = spreeMessage[PlayerTool.countKillStreak(killed)]
                //def kill = new Kill(killer:killer, killed:killed, friendlyfire:friendlyfire, deathCause:death)
                // No need to catch here no error can emerge
                //if(kill.hasErrors() || !kill.save(flush:true)) {
                //    log.error "[KillEvent] Could not persist kill: " + kill
                // }
                if (!friendlyfire) {
                    kills = killer.spreeCount
                    def spreeEnd = spreeMessage[killed.spreeCount]
                    spree = spreeMessage[kills]
                    if (spree != null) {
                        RCon.rcon("bigtext \"^2" + killer.getColorNick() + " ^7" + spree.text + " ^7(" + kills + " in a row)\"")
                    }
                    if (spreeEnd != null) {
                        RCon.rcon("say \"^2" + killer.getColorNick() + " ^7ended ^2" + killed.getColorNick() + "^7s " + spreeEnd.end + '"')
                    }

                    killer.killCount++
                    killer.spreeCount++
                    killer.gameKillCount++

                    def gameRatio = ((killer.gameKillCount + 1) / (killer.gameDeathCount + 1)) + 1
                    def totalRatio = ((killer.killCount + 1) / (killer.deathCount + 1)) + 1
                    killer.exp +=  calculateExpGain(killer, killed,
                        gameRatio, totalRatio)

                    if (killer.exp > killer.nextlevel) {
                        level(killer)
                    }

                    if (killer.hasErrors() || !killer.save(flush:true)) {
                        log.error "[KillEvnent] Unale to update player after gain, player: " + killer
                    }

                    killed.deathCount++
                    killed.gameDeathCount++
                    if (killed.hasErrors() || !killed.save(flush:true)) {
                        log.error "[KillEvnent] Unale to update player after gain, player: " + killed
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
        super.execute()
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

