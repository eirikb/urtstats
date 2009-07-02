urt.qconsole.path = "/path/to/fileqconsole.log"
urt.rcon.host = "localhost"
urt.rcon.port = 27960
urt.rcon.password = "password"

grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
    xml: ['text/xml', 'application/xml'],
    text: 'text/plain',
    js: 'text/javascript',
    rss: 'application/rss+xml',
    atom: 'application/atom+xml',
    css: 'text/css',
    csv: 'text/csv',
    all: '*/*',
    json: ['application/json','text/json'],
    form: 'application/x-www-form-urlencoded',
    multipartForm: 'multipart/form-data'
]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://www.changeme.com"
    }
    development {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
    test {
        grails.serverURL = "http://localhost:8080/${appName}"
    }
}

log4j = {
    debug 'grails.app.task'
    appenders {
        appender new org.apache.log4j.DailyRollingFileAppender(name:"file", fileName:"urtstats.log",
            datePattern: '\'_\'yyyy-MM-dd', layout:pattern(conversionPattern: '%d{ISO8601}\t%p\t%c:%L\t%m%n'))
        console name:'stdout', layout:pattern(conversionPattern: '%d{ISO8601}\t%p\t%c:%L\t%m%n')
    }
    root {
        info 'stdout', 'file'
        additivity = true
    }
}

     