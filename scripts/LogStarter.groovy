/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

def logParser = new no.eirikb.urtstats.logparser.LogParser()

def players = domain.urt.Player.findAllByUrtIDGreaterThanEquals(0)
players.each() {
    it.urtID = -1;
    it.save(flush:true)
}

Thread.start() {
    System.in.eachLine() { line ->
        switch (line) {
            case "quit":
            case "exit":
            no.eirikb.urtstats.utils.RCon.rcon("bigtext \"^7Stats are going down...\"")
            System.exit(0)
            break
            case "status":
            case "w":
            println no.eirikb.urtstats.utils.RCon.rcon("status", true)
            break
            default:
            println "Not a command: " + line
        }
    }
}

while (true) {
    logParser.execute()
    Thread.sleep(1000)
}

