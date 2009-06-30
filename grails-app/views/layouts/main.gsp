<html>
  <head>
    <title><g:layoutTitle default="Grddails" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
    <link rel="stylesheet" href="${resource(dir:'css',file:'stylesheet.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <g:layoutHead />
  <g:javascript library="application" />
</head>
<body>
  <div id="spinner" class="spinner" style="display:none;">
    <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
  </div>
  <div id="container">
    <jsec:isLoggedIn>
      <div>Logged in as: <jsec:principal/> (<g:link controller="auth" action="signOut">sign out</g:link>)</div>
    </jsec:isLoggedIn>
    <h1>${grailsApplication.config.urtstats.name} - ${grailsApplication.config.urtstats.version}</h1>
    <img src="${resource(dir:'images',file:'ut.png')}" width="100px" height="100px" alt="Grails" />
    <ul class="tabs" id="tabnav">
      <jsec:isNotLoggedIn>
        <li class="controller"><g:link controller="auth">Log in</g:link></li>
      </jsec:isNotLoggedIn>
      <li><a href="${resource(dir:'')}">Home</a></li>
      <li class="controller"><g:link controller="game">Game</g:link></li>

      <li class="controller"><g:link controller="player">Players</g:link></li>

      <jsec:hasRole  name="Administrator">
        <li class="controller"><g:link controller="admin">Admin</g:link></li>
      </jsec:hasRole>
    </ul>
    <g:layoutBody />
  </div>
</body>
</html>