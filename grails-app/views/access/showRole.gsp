<html>
  <head>
  <g:javascript library="prototype" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Show Role</title>
</head>
<body>
  <div class="nav">
    <span class="menuButton"><g:link action="list">Access panel</g:link></span>
  </div>
  <div class="body">
    <h1>Role: ${fieldValue(bean:role, field:'name')}</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    Users for role:
    <div id="userlist">
      <g:render template="userListTemplate" />
    </div>
    <div class="buttons">
      <g:form>
        <input type="hidden" name="id" value="${jsecRoleInstance?.id}" />
        <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
      </g:form>
    </div>
  </div>
</body>
</html>
