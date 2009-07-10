<h1>Permissions:</h1>
<div class="list">
  <table>
    <thead>
      <tr>  
    <g:sortableColumn property="id" title="Id" />
    <g:sortableColumn property="target" title="Target" />
    <g:sortableColumn property="actions" title="Actions" />
    <td>Remove</td>
    </tr>
    </thead>
    <tbody>
    <g:each in="${permissionList}" status="i" var="permission">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td>${fieldValue(bean:permission, field:'id')}</td>
        <td>${fieldValue(bean:permission, field:'target')}</td>
        <td>${fieldValue(bean:permission, field:'actions')}</td>
        <td><a href="${createLink(action:'removePermission', id:permission.id)}">Remove</a></td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
<div class="paginateButtons">
  <g:paginate total="${permissionListTotal}" />
</div>