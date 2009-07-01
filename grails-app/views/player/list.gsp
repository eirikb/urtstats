
<html>
  <head>
  <resource:autoComplete skin="default" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Player List</title>
</head>
<body>
      <div class="nav">
      <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="create" action="create">New Player</g:link></span>
    </div>
  <div class="body">
    Search:
    <g:form>
      <richui:autoComplete 
        name="name"
        action="${createLinkTo('dir': 'player/search')}"
        title="asdf"
        />
    </g:form>
    <h1>Player List</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="list">
      <table>
        <thead>
          <tr>
        <jsec:hasRole  name="Administrator">
          <g:sortableColumn property="id" title="Id" />
        </jsec:hasRole>

        <g:sortableColumn property="nick" title="Nick" />
        <jsec:hasRole  name="Administrator">
          <g:sortableColumn property="ip" title="Ip" />
        </jsec:hasRole>

        <g:sortableColumn property="level" title="Level" />

        <g:sortableColumn property="exp" title="Exp" />

        <g:sortableColumn property="nextlevel" title="Nextlevel" />

        </tr>
        </thead>
        <tbody>
        <g:each in="${playerInstanceList}" status="i" var="playerInstance">
          <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

          <jsec:hasRole  name="Administrator">
            <td><g:link action="show" id="${playerInstance.id}">${fieldValue(bean:playerInstance, field:'id')}</g:link></td>
          </jsec:hasRole>

          <td><g:link action="show" id="${playerInstance.id}">${fieldValue(bean:playerInstance, field:'nick')}</g:link></td>

          <jsec:hasRole  name="Administrator">
            <td>${fieldValue(bean:playerInstance, field:'ip')}</td>
          </jsec:hasRole>

          <td>${fieldValue(bean:playerInstance, field:'level')}</td>

          <td>${fieldValue(bean:playerInstance, field:'exp')}</td>

          <td>${fieldValue(bean:playerInstance, field:'nextlevel')}</td>

          </tr>
        </g:each>
        </tbody>
      </table>
    </div>
    <div class="paginateButtons">
      <g:paginate total="${playerInstanceTotal}" />
    </div>
  </div>
</body>
</html>
