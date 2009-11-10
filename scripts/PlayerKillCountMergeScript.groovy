/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */


println ""
println ""
println "Starting mergescript for Players kill count to Player itself..."

def total = domain.urt.Player.countByKillCount(0)
println "Total players: " + total
def i = 0

domain.urt.Player.findAllByKillCount(0).each() {
    println "" + (total - i++) + " - " + it.getNick() + " - " + domain.urt.Kill.countByKiller(it)
    it.killCount = domain.urt.Kill.countByKiller(it)
    it.deathCount = domain.urt.Kill.countByKilled(it)
    it.save(flush:true)
}

println "Merging complete."