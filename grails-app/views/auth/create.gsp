<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>Login</title>
  </head>
  <body>
    <h1>Create user</h1>
  <g:if test="${flash.message}">
    <div class="message">${flash.message}</div>
  </g:if>
  <g:form action="create">
    <table>
      <tbody>
        <tr>
          <td>Username:</td>
          <td><input type="text" name="username" value="" /></td>
        </tr>
        <tr>
          <td>Nick:</td>
          <td><input type="text" name="nick" value="" /></td>
        </tr>
        <tr>
          <td>PIN:</td>
          <td><input type="text" name="pin" value="" /></td>
        </tr>
        <tr>
          <td />
          <td><input type="submit" value="Create user" /></td>
        </tr>
      </tbody>
    </table>
  </g:form>
  <h2>Help / mini FAQ</h2>
  <b>What do I need to register?</b><br />
  You need to play on Graveyard Heaven in Urban Terror to get PIN.<br />
  Then you must combine the nick you used while playing with PIN given and IP address.<br />
  When those three combined match, a user will be created.<br /><br />
  <b>What is, and why do I need PIN?</b><br />
  PIN is used to identify a player from Urban Terror, so he/she can register on this page.<br />
  PIN is a five digit number you see when playing (private message from server).<br />
  Every time you change team or gear you will be given the PIN.<br /><br />
  <b>Can I create a user without PIN?</b><br />
  No. You must be a Urban Terror player (on Graveyard Heaven) to register<br /><br />
  <b>Must I redo this step for each different player I have?</b><br />
  No, you can add many players to one user.<br />
  But you must register this way the first time you register.
</body>
</html>
