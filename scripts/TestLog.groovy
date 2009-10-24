/*
 * ============================================================================
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <eirikb@google.com> wrote this file. As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return Eirik Brandtzæg
 * ============================================================================
 */

def logParser = new no.eirikb.urtstats.logparser.LogParser()
def ircbot = new no.eirikb.urtstats.ircbot.IrcBot()
ircbot.start()
logParser.synced = true
logParser.tail = new no.eirikb.utils.tail.Tail(new File("/home/eirikb/Desktop/qconsole.log"), false)
logParser.execute()