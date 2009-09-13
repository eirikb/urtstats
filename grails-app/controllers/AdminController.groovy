import no.eirikb.urtstats.logparser.LogParser

class AdminController {

    private  static logParser

    def index = {
        def parserStatus = "Not running"
        if (logParser != null) {
            parserStatus = "Parser running"
        }
        [parserStatus : parserStatus]
    }

    def startParser = {   
        if (logParser == null) {
            logParser = new LogParser()
            new Thread() {
                while (true) {
                    println "Thread run. " + this
                    logParser.execute()
                    Thread.sleep(1000)
                }
            }.start()
        }
        redirect(action:index)
    }
}
