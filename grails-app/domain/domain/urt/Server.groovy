package domain.urt

class Server {
    Date startLogging
    Date lastRestart
    String welcomeMessage

    static constraints = {
        startLogging(nullable:true)
        lastRestart(nullable:true)
        welcomeMessage(nullable:true)
    }
}
