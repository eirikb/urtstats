<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>ForumTopic List</title>
  </head>
  <body>
    <div class="nav">
      <span><g:link controller="forumGenre">ForumGenres</g:link></span>
      <jsec:isLoggedIn>
        <span class="menuButton"><g:link class="create" id="${genreID}" action="create">New ForumTopic</g:link></span>
      </jsec:isLoggedIn>
    </div>
    <div class="body">
      <h1>ForumTopic List</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <div class="list">
        <table>
          <thead>
            <tr>
          <g:sortableColumn property="name" title="Name" />
          </tr>
          </thead>
          <tbody>
          <g:each in="${forumTopicList}" status="i" var="forumTopic">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
              <td><g:link action="show" id="${forumTopic.id}">${fieldValue(bean:forumTopic, field:'id')}</g:link></td>
            <td>${fieldValue(bean:forumTopic, field:'name')}</td>
            </tr>
          </g:each>
          </tbody>
        </table>
      </div>
      <div class="paginateButtons">
        <g:paginate total="${forumTopicTotal}" />
      </div>
    </div>
  </body>
</html>
