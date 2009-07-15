
import no.eirikb.urtstats.logparser.LogParser

class LogJob  {
    def timeout = 5000l // execute job once in 5 seconds
    static logParser

    public LogJob() {
        if (logParser == null) {
            logParser = new LogParser()
        }
    }

    def execute() {
        logParser.execute()
    }
}
