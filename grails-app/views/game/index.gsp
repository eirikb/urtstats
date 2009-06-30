

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Game</title>
  </head>
  <body>
    <div class="body">
      <h1>Players currently playing</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="list">
        <table>
          <thead>
            <tr>
          <g:sortableColumn property="nick" title="Nick" />

          <g:sortableColumn property="level" title="Level" />

          </tr>
          </thead>
          <tbody>
          <g:each in="${playerInstanceList}" status="i" var="playerInstance">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

              <td><g:link controller="player" action="show" id="${playerInstance.id}">${fieldValue(bean:playerInstance, field:'nick')}</g:link></td>

            <td>${fieldValue(bean:playerInstance, field:'level')} - ${playerInstance}.</td>

            </tr>
          </g:each>
          </tbody>
        </table>
      </div>
      <div class="paginateButtons">
        <g:paginate total="${playerInstanceTotal}" />
      </div>
    </div>
  </body>
</html>
