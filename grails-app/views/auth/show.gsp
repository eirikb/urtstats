<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Show user</title>
  </head>
  <body>
    <div class="body">
      <h1>Show user</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name">Id:</td>
              <td valign="top" class="value">${fieldValue(bean:user, field:'id')}</td>
            </tr>
            <tr class="prop">
              <td valign="top" class="name">Username:</td>
              <td valign="top" class="value">${fieldValue(bean:user, field:'username')}</td>
            </tr>
            <tr class="prop">
              <td valign="top" class="name">Email:</td>
              <td valign="top" class="value">${fieldValue(bean:user, field:'email')}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </body>
</html>
