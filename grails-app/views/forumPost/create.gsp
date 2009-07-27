<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Create ForumPost</title>
  <resource:richTextEditor />
</head>
<body>
  <div class="nav">
    <span class="menuButton"><g:link class="list" controller="forumGenre" action="list">ForumGenre List</g:link></span>
    <span class="menuButton"><g:link class="list" controller="forumGenre" action="show" id="${forumGenre.getId()}">ForumGenre ${forumGenre.getName()}</g:link></span>
    <span class="menuButton"><g:link class="list" controller="forumTopic" action="show" id="${forumTopic.getId()}">ForumTopic ${forumTopic.getName()}</g:link></span>
  </div>
  <div class="body">
    <h1>Create ForumPost for ForumTopic ${forumTopic.getName()}</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${forumTopic}">
      <div class="errors">
        <g:renderErrors bean="${forumTopic}" as="list" />
      </div>
    </g:hasErrors>
    <g:form action="save" method="post" >
      <input type="hidden" name="topicID" value="${forumTopic.getId()}" />
      <div class="dialog">
        <table>
          <tbody>
            <tr class="prop">
              <td valign="top" class="name">
                <label for="body">Text:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:forumPost,field:'body','errors')}">
          <richui:richTextEditor name="body" value="${fieldValue(bean:forumPost,field:'body').decodeHTML()}" width="525" height="8" />
          </td>
          </tr>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><input class="save" type="submit" value="Create" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
