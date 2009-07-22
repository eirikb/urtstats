<html>
  <head>
    <title>UrTStats</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <h1>About UrTStats</h1>
    Quick mashup for info...<br /><br />
    <h3>What is UrTStats?</h3>
    <p>UrTStats is a stats-page for <a href="${resource(base:'http://urbanterror.net')}" target="_blank">Urban Terror</a>.</p>
    <p>The point of this project is not to create yet another stats page, but for me to learn
      <a href="${resource(base:'http://grails.org')}" target="_blank">Grails</a>.</p>
    <p>Note! Everything is written by hand, I'm _not_ using any bots or software such as B3 or XLR.</p>
    <p>The reason for this is much that Grails use GORM (Hibernate), and it's better to use this to insert data into database</p>
    <p>My editor of choise is <a href="${resource(base:'http://netbeans.org')}" target="_blank">NetBeans</a></p>
    <br />
    <h3>Project layout</h3>
    <p>So the project is split up in mainly three parts, the set up (hosting, email), this page and the parser (parsing game log files).</p>
    <ul>
      <li>
        <p><b>Hosting:</b></p>
        <p>Everything related to this project is hosted on a <a href="${resource(base:'http://server.lu')}" target="_blank">server.lu</a>
          dedicated server, in Luxemburg.</p>
        <p><a href="${resource(base:'http://glassfish.org')}" target="_blank">Running GlassFish V3</a> for hosting this page,
          <a href="${resource(base:'http://redmine.org')}" target="_blank">Redmine</a> for project hosting and
          <a href="${resource(base:'http://mercurial.selenic.com/wiki/')}" target="_blank">Mercurial</a> for SCM</p>
        <p>For other projects outside grails <a href="${resource(base:'http://maven.apache.org/')}" target="_blank">Maven</a>
          is used for building</p>
        <p>The server is using postfix and dovecot for mail</p>
        <p>Server is hosting three public Urban Terror servers</p>
      </li>
      <li>
        <p><b>Page:</b></p>
        <p>The page is written in Groovy with the Grails framework.</p>
        <p>The stats system is ideal for learning a MVC framework such as grails.</p>
        <p>It's interesting, can adopt many components and rpidly fills up the database</p>
      </li>
      <li>
        <p><b>Parser:</b></p>
        <p>The parser is written in Groovy.</p>
        <p>It's a simple little thing that tails the Urban Terror log file (qconsole.log) and parse new lines</p>
        <p>Parser source is inside the page grails project, and is set to start up when the page is launched (BootStrap)</p>
      </li>
    </ul>

    <br />
    <h3>Version history</h3>
    <ul>
      <li>
        <p><b>0.4 (In progress):</b></p>
        <p>Fix stuff on the page</p>
      </li>
      <li>
        <p><b>0.3:</b></p>
        <p>Total makeover for the parser.</p>
        <p>The parser is now splitted into many different classes for each "event"</p>
        <p>Unit tests for all events are written and runnable</p>
      </li>
      <li>
        <p><b>0.2:</b></p>
        <p>A more advanced page where players can register user</p>
        <p>Permission system for page and ingame rcon</p>
      </li>
      <li>
        <p><b>0.1:</b></p>
        <p>Create a working parser, simple program that parses the logs and save to database</p>
        <p>Simple page that can start stop the script</p>
      </li>
      <li>
        <p><b>0.0:</b></p>
        <p>Setup the server with GlassFish and make everything run</p>
      </li>
    </ul>

    <br />
    <h3>About me</h3>
    <img src="${resource(dir: 'images', file: 'me.jpg')}"  />
    <p>I was born <prettytime:display date="${myBirtDay}" />.</p>
<p>From Norway, educated as computer engineer thingy.</p>
<p>Email: eirikb (AT) urtstats.net</p>
</body>
</html>