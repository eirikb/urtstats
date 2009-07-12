

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>ForumGenre List</title>
  </head>
  <body>
  <jsec:hasPermission permission="${new org.jsecurity.authz.permission.WildcardPermission('forum:genre:create')}">
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
        <td>Topic count</td>
        </tr>
        </thead>
        <tbody>
        <g:each in="${forumGenreList}" status="i" var="forumGenre">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
          <g:link action="show" id="${forumGenre.id}">${fieldValue(bean:forumGenre, field:'name')}</g:link>
          forumGenre?.topics?.size()
          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${forumGenreTotal}" />
    </div>
  </div>
</body>
</html>
