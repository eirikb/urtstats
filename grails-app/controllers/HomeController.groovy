import domain.news.News
import domain.urt.Player

class HomeController {

    def index = {
        [ newsList:News.list( sort:"lastUpdated", order:"desc" ), newsInstanceTotal: 1000 ]
    }
}
