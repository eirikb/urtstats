<html>
  <head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Show user</title>
</head>
<body>
  <div class="nav">
    <span class="menuButton"><g:link action="list">Access panel</g:link></span>
  </div>
  <div class="body">
    <h1>User: ${fieldValue(bean:user, field:'username')}</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:form action="addRemoveRoleTouser">
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
                      checked="${JsecUserRoleRel.findByUserAndRole(user, role)}" /></td>
      </tr>
    </g:each>
    </tbody>
  </table>
</div>
<div class="paginateButtons">
  <g:paginate total="${roleListTotal}" />
</div>
      <g:hiddenField name="userID" value="${user.id}" />
      <g:submitButton name="update" value="Update" />
    </g:form>
  </div>
</body>
</html>
