

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Access controller</title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><g:link class="create" action="create">New Role</g:link></span>
    </div>
    <div class="body">
      This is a _very_ simple tool for giving users access.
      A better tool might be created - who knows.
      <g:render template="roleListTemplate"/>
      <g:render template="userListTemplate"/>
    </div>
  </body>
</html>
