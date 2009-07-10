class HomeController {

    def index = {
        params.max = Math.min( params.max ? params.max.toInteger() : 10,  100)
        [ newsList: News.list( params ), newsInstanceTotal: News.count() ]
    }
}
