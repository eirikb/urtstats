

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Home - news</title>
  </head>
  <body>
    <div class="body">
      <h1>News List</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:each in="${newsList}" status="i" var="news">
        <h1>${fieldValue(bean:news, field:'title')}</h1>
${fieldValue(bean:news.getAuthor(), field:'username')}
        (<prettytime:display date="${news.getCreateTime()}" />)
${fieldValue(bean:news, field:'head').decodeHTML()}
        <hr>
${fieldValue(bean:news, field:'body').decodeHTML()}
      </g:each>
    </div>
  </body>
</html>
