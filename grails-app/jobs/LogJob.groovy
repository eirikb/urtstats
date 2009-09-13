
import no.eirikb.urtstats.logparser.LogParser

class LogJob  {
    def startDelay = 30000
    def repeatCount = 1
    final static logParser = new LogParser()

    public LogJob() {

    }

    def execute() {
        println "TESTETSET"
        logParser.start()
        // logParser.execute()
    }
}
