
import no.eirikb.urtstats.logparser.LogParser

class LogJob  {
    def startDelay = 30000
    def timeout = 1000
    static logParser

    public LogJob() {

    }

    def execute() {
        if (logParser == null) {
            logParser = new LogParser()
        }
        logParser.execute()
    }
}
