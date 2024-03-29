

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>ForumGenre List</title>
  </head>
  <body>
  <jsec:hasPermission permission="${new org.jsecurity.grails.JsecBasicPermission('forum', 'genre:create')}">
    <div class="nav">
      <span class="menuButton"><g:link class="create" action="create">New ForumGenre</g:link></span>
    </div>
  </jsec:hasPermission>
  <div class="body">
    <h1>ForumGenre List</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
      <table>
        <thead>
          <tr>
        <g:sortableColumn property="name" title="Name" />
        <th>Amount of topics</th>
        </tr>
        </thead>
        <tbody>
        <g:each in="${forumGenreList}" status="i" var="forumGenre">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
            <td><g:link action="show" id="${forumGenre[0]}">${forumGenre[1]}</g:link></td>
          <td>${forumGenre[2]}</td>
          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${forumGenreTotal}" />
    </div>

    <g:haveUnreadPosts>
      <g:form>
        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Clear" /></span>
      </g:form>
      <div class="list">
        <table>
          <thead>
            <tr>
              <th>Author</th>
              <th>Topic</th>
            </tr>
          </thead>
          <tbody>
          <g:each in="${forumPostList}" status="i" var="forumPost">
            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
              <td><g:link action="show" controller="forumTopic" id="${forumPost.topic.id}">${forumPost.user}</g:link></td>
            <td><g:link action="show" controller="forumTopic" id="${forumPost.topic.id}">${fieldValue(bean:forumPost.topic, field:'name')}</g:link></td>
            </tr>
          </g:each>
          </tbody>
        </table>
      </div>
    </g:haveUnreadPosts>
  </div>
</body>
</html>
