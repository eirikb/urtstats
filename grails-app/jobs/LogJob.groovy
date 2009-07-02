
class LogJob  {
    def timeout = 5000l // execute job once in 5 seconds
    boolean transactional = true
    def logger

    public LogJob() {
      
        if (logger == null) {
            logger = new Logger()
        }
    }

    def execute() {
          log.debug "test"
        logger.execute()
    }
}
