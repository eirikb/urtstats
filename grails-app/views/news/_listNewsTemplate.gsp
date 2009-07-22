<g:each in="${newsList}" status="i" var="news">
  <h1>${fieldValue(bean:news, field:'title')}</h1>
  <jsec:hasPermission permission="${new org.jsecurity.grails.JsecBasicPermission('news', 'edit')}">
    <g:link controller="news" action="edit" id="${news.id}">[edit]</g:link>
  </jsec:hasPermission>
  <b>Author:</b>${fieldValue(bean:news.getAuthor(), field:'firstname')} ${fieldValue(bean:news.getAuthor(), field:'lastname')}
(${fieldValue(bean:news.getAuthor(), field:'username')})
   - <b>Created:</b> <prettytime:display date="${news.getDateCreated()}" />
   - <b>Last modified:</b> <prettytime:display date="${news.getLastUpdated()}" />
${fieldValue(bean:news, field:'head').decodeHTML()}

${fieldValue(bean:news, field:'body').decodeHTML()}
</g:each>