// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }

foo.bar.hello = "Hello!"

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

log4j {
    appender.stdout = "org.apache.log4j.ConsoleAppender"
    appender.'stdout.layout'="org.apache.log4j.PatternLayout"
    appender.'stdout.layout.ConversionPattern'='[%r] %c{2} %m%n'

    appender.logfile = "org.apache.log4j.DailyRollingFileAppender "
    appender.'logfile.File' = "/home/eirikb/logfile.log"
    appender.'logfile.layout' = "org.apache.log4j.PatternLayout"
    appender.'logfile.layout.ConversionPattern' = '%d{[ dd.MM.yy HH:mm:ss.SSS]} [%t] %-5p %c %x - %m%n'

    rootLogger="error,stdout"
    logger {
        grails="info,stdout,logfile"
        org {
            codehaus.groovy.grails.web.servlet= "info,stdout,logfile"  //  controllers
            codehaus.groovy.grails.web.pages="info,stdout,logfile" //  GSP
            codehaus.groovy.grails.web.sitemesh="info,stdout,logfile" //  layouts
            codehaus.groovy.grails."web.mapping.filter"="info,stdout,logfile" // URL mapping
            codehaus.groovy.grails."web.mapping"="info,stdout,logfile" // URL mapping
            codehaus.groovy.grails.commons="info,stdout,logfile" // core / classloading
            codehaus.groovy.grails.plugins="info,stdout,logfile" // plugins
            codehaus.groovy.grails.orm.hibernate= "info,stdout,logfile" // hibernate integration
            springframework="off,stdout,logfile"
            hibernate="off,stdout,logfile"
           // org.codehaus.groovy.grails.plugins.acegi= "all,stdout,logfile"
        }
    }
}

// log4j configuration
/**
log4j = {
// Example of changing the log pattern for the default console
// appender:
//
//appenders {
//    console name:'stdout', layout:pattern(conversionPattern: '%c{2} %m%n')
//}

error  'org.codehaus.groovy.grails.web.servlet',  //  controllers
'org.codehaus.groovy.grails.web.pages', //  GSP
'org.codehaus.groovy.grails.web.sitemesh', //  layouts
'org.codehaus.groovy.grails.web.mapping.filter', // URL mapping
'org.codehaus.groovy.grails.web.mapping', // URL mapping
'org.codehaus.groovy.grails.commons', // core / classloading
'org.codehaus.groovy.grails.plugins', // plugins
'org.codehaus.groovy.grails.orm.hibernate', // hibernate integration
'org.springframework',
'org.hibernate'

warn   'org.mortbay.log'
}
 */

     