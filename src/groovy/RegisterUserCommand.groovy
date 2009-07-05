public class RegisterUserCommand {

    String username
    String password
    String password2
    String nick
    Integer pin


    static constraints={
        username(nullable:false, blank:false, validator:{val,obj ->
                if (JsecUser.findByUsername(val) != null)
                return ['field.already.exists','Username ' + val,'username']
            })
        password(nullable:false, blank:false)
        password2(blank:false,validator:{val,obj ->
                if(!(val==obj.password))
                return ['fields.does.not.match.message','Cofirm Password ','Password']
            })
        nick(blank:false, nullable:false)
        pin(blank:false, nullable:false)
    }

}