<h1>Users:</h1>
<div class="list">
  <table>
    <thead>
      <tr>
    <g:sortableColumn property="id" title="Id" />
    <g:sortableColumn property="username" title="Username" />
    </tr>
    </thead>
    <tbody>
    <g:each in="${userList}" status="i" var="user">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td><g:link action="showUser" id="${user.id}">${fieldValue(bean:user, field:'id')}</g:link></td>
      <td>${fieldValue(bean:user, field:'username')}</td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
<div class="paginateButtons">
  <g:paginate total="${userListTotal}" />
</div>