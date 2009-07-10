<html>
  <head>
  <g:javascript library="prototype" />
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
      <g:render template="roleComboListTemplate" />
      <g:hiddenField name="userID" value="${user.id}" />
      <g:submitButton name="update" value="Update" />
    </g:form>
  </div>
</body>
</html>
