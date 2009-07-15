class Chat {
    static belongsTo = Player

    Player player

    String message
    Boolean teamMessage

    Date dateCreated

    static constraints = {
    }
}
