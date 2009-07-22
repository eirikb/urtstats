class AboutController {

    def index = {
        def calendar = Calendar.getInstance()
        calendar.set(1987, 1, 7)
        [myBirtDay:calendar.getTime()]
    }
}
