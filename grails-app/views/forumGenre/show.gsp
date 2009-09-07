

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Show ForumGenre</title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><g:link class="list" action="list">ForumGenre List</g:link></span>
      <jsec:isLoggedIn>
        <span class="menuButton"><g:link class="create" controller="forumTopic" action="create" id="${forumGenre.getId()}">New ForumTopic</g:link></span>
      </jsec:isLoggedIn>
    </div>
    <div class="body">
      <h1>${fieldValue(bean:forumTopicInstance, field:'name')}</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <h1>ForumGenre: ${forumGenre.getName()}</h1>
      <div class="list">
        <table>
          <thead>
            <tr>
          <g:sortableColumn property="name" title="Name" />
          <g:sortableColumn property="user" title="Author" />
          <th>Amount of posts</th>
          </tr>
          </thead>
          <tbody>
          <g:each in="${forumTopicList}" status="i" var="forumTopic">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
              <td><g:link controller="forumTopic" action="show" id="${forumTopic[0]}">${forumTopic[1]}</g:link></td>
            <td>${forumTopic[2]}</td>
            <td>${forumTopic[3]}</td>
            </tr>
          </g:each>
          </tbody>
        </table>
      </div>
      <div class="paginateButtons">
        <g:paginate total="${forumTopicTotal}" />
      </div>
    </div>
  </div>
</body>
</html>
