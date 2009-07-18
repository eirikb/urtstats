
import no.eirikb.urtstats.logparser.LogParser

class LogJob  {
    def startDelay = 30000
    def timeout = 1000
    final static logParser = new LogParser()

    public LogJob() {

    }

    def execute() {
        logParser.execute()
    }
}
