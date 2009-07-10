<g:each in="${newsList}" status="i" var="news">
  <h1>${fieldValue(bean:news, field:'title')}</h1>
  <jsec:hasPermission permission="${new org.jsecurity.authz.permission.WildcardPermission('news:edit')}">
    <g:link controller="news" action="edit" id="${news.id}">[edit]</g:link>
  </jsec:hasPermission>
${fieldValue(bean:news.getAuthor(), field:'username')}
  (<prettytime:display date="${news.getCreateTime()}" />)
${fieldValue(bean:news, field:'head').decodeHTML()}

${fieldValue(bean:news, field:'body').decodeHTML()}
</g:each>