<html>
  <head>
  <resource:autoComplete skin="default" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Player List</title>
</head>
<body>
<jsec:hasRole  name="ADMIN">
  <div class="nav">
    <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
    <span class="menuButton"><g:link class="create" action="create">New Player</g:link></span>
  </div>
</jsec:hasRole>
<div class="body">
  Search:
  <g:form>
    <richui:autoComplete
      name="name"
      action="${resource('dir': 'player/search')}"
      onItemSelect="document.location.href = \'${resource(dir: 'player/show')}/\' + id;"
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
      <g:sortableColumn property="nick" title="Nick" />
      <jsec:hasRole  name="ADMIN">
        <g:sortableColumn property="ip" title="Ip" />
      </jsec:hasRole>

      <g:sortableColumn property="level" title="Level" />

      <g:sortableColumn property="exp" title="Exp" />

      <g:sortableColumn property="nextlevel" title="Nextlevel" />

      <g:sortableColumn property="kills.size" title="Kills" />

      <g:sortableColumn property="headshots" title="Headshots" />

      </tr>
      </thead>
      <tbody>
      <g:each in="${playerList}" status="i" var="player">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

          <td><g:link action="show" id="${player.id}">${player.nick}</g:link></td>

        <jsec:hasRole  name="ADMIN">
          <td>${player.ip}</td>
        </jsec:hasRole>

        <td>${player.level}</td>

        <td>${player.exp}</td>

        <td>${player.nextlevel}</td>

        <td>${player.kills}</td>

        <td>${player.headshots}</td>
        </tr>
      </g:each>
      </tbody>
    </table>
  </div>
  <div class="paginateButtons">
    <g:paginate total="${playerTotal}" />
  </div>
</div>
</body>
</html>
