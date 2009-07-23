

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Show ForumTopic</title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="list" action="list">ForumTopic List</g:link></span>
      <span class="menuButton"><g:link class="create" action="create">New ForumTopic</g:link></span>
    </div>
    <div class="body">
      <h1>${fieldValue(bean:forumTopicInstance, field:'name')}</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>

    </div>
    <div class="buttons">
      <g:form>
        <input type="hidden" name="id" value="${forumTopicInstance?.id}" />
        <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
      </g:form>
    </div>
  </div>
</body>
</html>
