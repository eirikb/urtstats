import domain.news.News
import domain.urt.Player

class HomeController {

    def index = {
        println request.getRemoteAddr()
        [ newsList:News.list( sort:"lastUpdated", order:"desc" ), newsInstanceTotal: News.count() ]
    }
}
