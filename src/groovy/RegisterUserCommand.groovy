public class RegisterUserCommand {

    String password
    String password2
    String nick
    Integer pin


    static constraints={
        password(nullable:false, blank:false)
        password2(blank:false, validator:{val,obj ->
                if(!(val==obj.password))
                return ['registerusercommand.does.not.match.message','Cofirm Password ','Password']
            })
        nick(blank:false, nullable:false)
        pin(nullable:false, validator:{val,obj ->
                if(Player.findByNickIlikeAndPin(obj.nick, obj.pin) == null)
                return ['registerusercommand.nick.and.pin.not.match','nick','nick']
            })
    }
}