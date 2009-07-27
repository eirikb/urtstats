

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Show ForumTopic</title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><g:link class="list" controller="forumGenre" action="list">ForumGenre List</g:link></span>
      <span class="menuButton"><g:link class="list" controller="forumGenre" action="show" id="${forumGenre.getId()}">ForumGenre ${forumGenre.getName()} List</g:link></span>
      <jsec:isLoggedIn>
        <span class="menuButton"><g:link class="reply" controller="forumPost" action="create" id="${forumTopic.getId()}">Reply</g:link></span>
      </jsec:isLoggedIn>
    </div>
    <div class="body">
      <h1>${fieldValue(bean:forumTopicInstance, field:'name')}</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>

      <h1>${forumTopic.getName()}</h1>

      <g:each in="${forumPostList}" var="forumPost">
        <prettytime:display date="${forumPost.getLastUpdated()}" />
${fieldValue(bean:forumPost.getUser(), field:'firstname')} ${fieldValue(bean:forumPost.getUser(), field:'lastname')}
        (<b>${fieldValue(bean:forumPost.getUser(), field:'username')}</b>) wrote:

${fieldValue(bean:forumPost, field:'body').decodeHTML()}
        <br />

      </g:each>

    </div>
  </body>
</html>
