<html>
  <head>
    <title><g:layoutTitle default="Grddails" /></title>
    <link rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" />
    <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
  <g:layoutHead />
  <resource:reflectionImage />
  <g:javascript library="application" />
</head>
<body>
  <div id="spinner" class="spinner" style="display:none;">
    <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
  </div>
  <div id="container">

    <script type="text/javascript"><!--
  google_ad_client = "pub-1103417098772225";
  /* 234x60, created 9/26/09 */
  google_ad_slot = "5616484798";
  google_ad_width = 234;
  google_ad_height = 60;
  //-->
    </script>
    <script type="text/javascript"
            src="http://pagead2.googlesyndication.com/pagead/show_ads.js">
    </script>
    <script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
    </script>
    <script type="text/javascript">
    try {
    var pageTracker = _gat._getTracker("UA-5799985-2");
    pageTracker._trackPageview();
    } catch(err) {}</script>

    <h1>${grailsApplication.metadata['app.name']} - ${grailsApplication.metadata['app.version']}</h1>
    Stats for <g:link target="_blank" base="http://www.urbanterror.net">Urban Terror</g:link>
    <jsec:isLoggedIn>
      <br />
      <div>Logged in as: <jsec:principal/> (<g:link controller="auth" action="signOut">sign out</g:link>)</div>
      <div><g:haveUnreadPosts><img src="${resource(dir:'images/skin',file:'forum_newmessage.png')}"/>
          <g:link controller="forumGenre">You have unread posts!</g:link>
        </g:haveUnreadPosts></div>
    </jsec:isLoggedIn>
    <richui:reflectionImage src="${resource(dir: 'images', file: 'ut.png')}"  width="125px" height="110px" />

    <ul class="tabs" id="tabnav">
      <jsec:isNotLoggedIn>
        <li class="controller"><g:link controller="auth">Log in</g:link></li>
      </jsec:isNotLoggedIn>

      <jsec:isLoggedIn>
        <li class="controller"><g:link controller="profile">Profile</g:link></li>
      </jsec:isLoggedIn>

      <li><a href="${resource(dir:'/')}">Home</a></li>

      <li class="controller"><g:link controller="forumGenre">Forum</g:link></li>

      <li class="controller"><g:link controller="game">Game</g:link></li>

      <li class="controller"><g:link controller="player">Players</g:link></li>

      <li class="controller"><g:link controller="chat">Chat</g:link></li>

      <li class="controller"><g:link controller="demo">DemoPot</g:link></li>

      <li class="controller"><g:link controller="about">About</g:link></li>

      <jsec:hasRole  name="ADMIN">
        <li class="controller"><g:link controller="admin">Admin</g:link></li>
      </jsec:hasRole>
    </ul>
    <g:layoutBody />
  </div>
</body>
</html>