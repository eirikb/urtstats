

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Edit ForumTopic</title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="list" action="list">ForumTopic List</g:link></span>
      <span class="menuButton"><g:link class="create" action="create">New ForumTopic</g:link></span>
    </div>
    <div class="body">
      <h1>Edit ForumTopic</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${forumTopicInstance}">
        <div class="errors">
          <g:renderErrors bean="${forumTopicInstance}" as="list" />
        </div>
      </g:hasErrors>
      <g:form method="post" >
        <input type="hidden" name="id" value="${forumTopicInstance?.id}" />
        <input type="hidden" name="version" value="${forumTopicInstance?.version}" />
        <div class="dialog">
          <div class="buttons">
            <span class="button"><g:actionSubmit class="save" value="Update" /></span>
            <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
          </div>
      </g:form>
    </div>
  </body>
</html>
