
class LogJob  {
    def timeout = 5000l // execute job once in 5 seconds
    boolean transactional = true
    static logger

    public LogJob() {
        if (logger == null) {
            println "CONSTRUCTOR!"
            logger = new Logger()

        } else {
            println "Logger exists"
        }
    }

    def execute() {
        logger.execute()
    }
}
