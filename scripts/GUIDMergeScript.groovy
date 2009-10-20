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
println "Starting mergescript for GUID."
println "Storing all GUID for validation after merge..."
def list = [:]
domain.urt.Player.list().each {
    list[it.id] = it.getGuid()
}
println "Done."

println "Merging..."
def tot = domain.urt.Player.count()
def percent = 0
def i = -1
domain.urt.Player.list().each {
    def g = domain.urt.GUID.findByGuid(it.getGuid())
    if (g != null) {
        println "GUID already exist for " + it + " (" + g.getPlayer() + ")"
    } else {
        new domain.urt.GUID(player:it, guid:it.getGuid()).save()
    }

    i++
    def percent2 = (int)((i / tot) * 100)
    if (percent2 != percent) {
        percent = percent2
        println "Progress: " + percent + "%"
    }
}

println "Checking GUID against validation list..."
domain.urt.Player.list().each() {
    if (it.getGuid() != list[it.id]) {
        println "GUID not correct for player: " + it
    }
}
println "Done."

println ""
println "Merging complete."