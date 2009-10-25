

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Edit ForumPost</title>
  </head>
  <body>
    <div class="nav">

    </div>
    <div class="body">
      <h1>Edit ForumPost</h1>
      This is a test of the FCKEditor.
      If this editor prove to be better than the one in RichUI I will swtich to this.
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${forumPostInstance}">
        <div class="errors">
          <g:renderErrors bean="${forumPostInstance}" as="list" />
        </div>
      </g:hasErrors>
      <g:form method="post" >
        <input type="hidden" name="id" value="${forumPostInstance?.id}" />
        <input type="hidden" name="version" value="${forumPostInstance?.version}" />
        <div class="dialog">
          <fckeditor:editor
            name="text"
            width="100%"
            height="400"
            toolbar="Basic"
            fileBrowser="default">
${forumPostInstance?.body}
          </fckeditor:editor>
        </div>
        <div class="buttons">
          <span class="button"><g:actionSubmit class="save" value="Update" /></span>
          <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
        </div>
      </g:form>
    </div>
  </body>
</html>
