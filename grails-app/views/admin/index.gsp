
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Admin</title>
  </head>
  <body>
    This is a very simple admin panel.<br />
    It's based on scaffolding and showing all available controllers.<br />
    List of all controllers:<br /><br />
    <ul>
      <g:each var="c" in="${grailsApplication.controllerClasses}">
        <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
      </g:each>
    </ul>
  </body>
</html>

