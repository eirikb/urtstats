class Chat {
    static belongsTo = Player

    Player player

    String message
    Boolean teamMessage
    Date createTime = new Date()

    static constraints = {
    }
}
