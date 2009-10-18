package domain.demo

class Tag {

    static hasMany = [tags:DemoTagRel]
    
    String name
   
    static constraints = {
    }
}
