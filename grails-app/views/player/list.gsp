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
        <td>IP</td>
      </jsec:hasRole>

      <g:sortableColumn defaultOrder="desc" property="level" title="Level" />

      <g:sortableColumn defaultOrder="desc" property="exp" title="Exp" />

      <g:sortableColumn defaultOrder="desc" property="nextlevel" title="Nextlevel" />

      <g:sortableColumn defaultOrder="desc" property="kills" title="Kills" />

      </tr>
      </thead>
      <tbody>
      <g:each in="${players}" status="i" var="player">
        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
        <g:if test="${!(player instanceof domain.urt.Player)}">
${player = new domain.urt.Player(id:player[0], nick:player[1], level:player[2], exp:player[3], nextlevel:player[4]) }
        </g:if>
        <td><g:link action="show" id="${player.getId()}">${player.getNick()}</g:link></td>

        <jsec:hasRole  name="ADMIN">
          <td>${player.getIp()}</td>
        </jsec:hasRole>

        <td>${player.getLevel()}</td>

        <td>${player.getExp()}</td>

        <td>${player.getNextlevel()}</td>

        <td>${infoMap.(player?.getId())?.kills}</td>
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
