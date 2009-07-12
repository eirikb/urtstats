<html>
  <head>
    <title>UrTStats</title>
    <meta name="layout" content="main" />
  </head>
  <body>
    <h1 style="margin-left:20px;">Urban Terror - Riyadh Heaven</h1>
    <p style="margin-left:20px;width:80%">Internet is a cloud</p>
  <jsec:hasPermission permission="${new org.jsecurity.grails.JsecBasicPermission('news', 'create')}">
    <g:link controller="news" action="create">Create news</g:link>
  </jsec:hasPermission>
  <g:render template="/news/listNewsTemplate" />
</body>
</html>