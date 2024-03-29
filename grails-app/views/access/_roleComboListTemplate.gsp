<h1>Roles:</h1>
<div class="list">
  <table>
    <thead>
      <tr>
    <g:sortableColumn property="id" title="Id" />
    <g:sortableColumn property="name" title="Name" />
    <td>Access</td>
    </tr>
    </thead>
    <tbody>
    <g:each in="${roleList}" status="i" var="role">
      <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <td><g:link action="showRole" id="${role.id}">${fieldValue(bean:role, field:'id')}</g:link></td>
      <td>${fieldValue(bean:role, field:'name')}</td>
      <td><g:checkBox name="role-${role.id}" value="${role.id}"
                      checked="${domain.security.JsecUserRoleRel.findByUserAndRole(user, role)}" /></td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
<div class="paginateButtons">
  <g:paginate total="${roleListTotal}" />
</div>