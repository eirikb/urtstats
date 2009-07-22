import domain.news.News

class HomeController {

    def index = {
        [ newsList:News.list( sort:"lastUpdated", order:"desc" ), newsInstanceTotal: News.count() ]
    }
}
