
class LogJob  {
    def timeout = 5000l // execute job once in 5 seconds
    boolean transactional = true
    static logger

    public LogJob() {

    }

    def execute() {
        if (logger == null) {
            logger = new Logger()
            logger.execute()
        }

    }
}
