

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
      This is a _very_ simple tool for giving users access. <br />
      A better tool might be created - who knows. <br />
      To add a user to a role, select user.
      <g:render template="roleListTemplate"/>
      <g:render template="userListTemplate"/>
    </div>
  </body>
</html>
