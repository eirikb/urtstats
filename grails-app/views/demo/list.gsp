
<%@ page import="domain.demo.Demo" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
  <g:javascript library="flowplayer-3.1.4.min" />
  <title>Demo List</title>
</head>
<body>
<jsec:hasPermission permission="${new org.jsecurity.grails.JsecBasicPermission('demo', 'create')}">
  <div class="nav">
    <span class="menuButton"><g:link class="create" action="create">[New Demo]</g:link></span>
  </div>
</jsec:hasPermission>

<div class="body">
  <h1>Demo List</h1>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  <div class="list">
    <g:each in="${demoInstanceList}" status="i" var="demoInstance">
      <script>
  flowplayer("${demoInstance.id}", "${resource(dir:'flash',file:'flowplayer-3.1.4.swf')}", {
  clip: {autoPlay: false, autoBuffering: false, provider:'pseudo'}});
      </script>
      <div class="dialog">
        <h1>${fieldValue(bean:demoInstance, field:'name')}</h1>
${demoInstance.getUser()} uploaded this demo <prettytime:display date="${demoInstance.getLastUpdated()}" />
        <a
          href="${createLink(action:'getFile', id:demoInstance.id)}.flv"
          style="display:block;width:640px;height:360px"
          id="${demoInstance.id}">
        </a>
${demoInstance.getDescription()}
      </div>
    </g:each>
  </div>
  <div class="paginateButtons">
    <g:paginate total="${demoInstanceTotal}" />
  </div>
</div>
</body>
</html>