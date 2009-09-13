
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Admin</title>
  </head>
  <body>
    Admin panel
    <ul>
        <li class="controller"><g:link controller="access">Edit access (user, role, permission)</g:link></li>
        <li class="controller"><a href="${resource(dir:'admin/startParser')}">Start parser (${parserStatus})</a></li>
    </ul>
  </body>
</html>

