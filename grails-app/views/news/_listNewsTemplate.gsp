<g:each in="${newsList}" var="news">
  <h1>${fieldValue(bean:news, field:'title')}</h1>
  <jsec:hasPermission permission="${new org.jsecurity.grails.JsecBasicPermission('news', 'edit')}">
    <g:link controller="news" action="edit" id="${news.id}">[edit]</g:link>
  </jsec:hasPermission>
  <prettytime:display date="${news.getLastUpdated()}" />
${fieldValue(bean:news.getAuthor(), field:'firstname')} ${fieldValue(bean:news.getAuthor(), field:'lastname')}
  (<b>${fieldValue(bean:news.getAuthor(), field:'username')}</b>) wrote:

${fieldValue(bean:news, field:'head').decodeHTML()}

${fieldValue(bean:news, field:'body').decodeHTML()}
</g:each>
<div class="paginateButtons">
  <g:paginate total="${newsInstanceTotal}" />
</div>