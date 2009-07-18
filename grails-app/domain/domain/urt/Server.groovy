package domain.urt

class Server {
    int ident
    Date startLoggingDate = new Date()
    Date lastRestartDate = new Date()
    String welcomeMessage

    static constraints = {
        startLoggingDate(nullable:true)
        lastRestartDate(nullable:true)
        welcomeMessage(nullable:true)
    }
}
