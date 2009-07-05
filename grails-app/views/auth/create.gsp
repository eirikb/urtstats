<html>
  <head>
  <g:javascript library="prototype" />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <meta name="layout" content="main" />
  <title>Create user</title>
</head>
<body>
  <h1>Create user</h1>
<g:if test="${flash.message}">
  <div class="message">${flash.message}</div>
</g:if>

<g:hasErrors bean="${cmd?.errors}">
  <div class="errors">
    <g:renderErrors bean="${cmd}"/>
  </div>
</g:hasErrors>
<g:form action="save" >
  <div class="dialog">
    <table>
      <tbody>

        <tr class="prop">
          <td valign="top" class="name">
            <label for="username">Username:</label>
          </td>
          <td valign="top" class="value ${hasErrors(bean:cmd,field:'username','errors')}">
            <input type="text" id="username" name="username" value="${fieldValue(bean:cmd,field:'username')}"/>
            <div id="message">
              <g:render template="checkUserTemplate" model="[user:user]" />
            </div>
            <div id="error"></div>
      <g:remoteField action="checkUsername" update="message" name="username" value=""/>
      </td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name">
          <label for="password" password="true">Password:</label>
        </td>
        <td valign="top" class="value ${hasErrors(bean:cmd,field:'password','errors')}">
          <input type="password" id="password" name="password" value="${fieldValue(bean:cmd,field:'password')}"/>
        </td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name">
          <label for="password2" password="true">Password Again:</label>
        </td>
        <td valign="top" class="value ${hasErrors(bean:cmd,field:'password2','errors')}">
          <input type="password" id="password2" name="password2" value="${fieldValue(bean:cmd,field:'password2')}"/>
        </td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name">
          <label for="nick">Nick:</label>
        </td>
        <td valign="top" class="value ${hasErrors(bean:cmd,field:'nick','errors')}">
          <input type="text" id="nick" name="nick" value="${fieldValue(bean:cmd,field:'nick')}"/>
          (From game)
        </td>
      </tr>

      <tr class="prop">
        <td valign="top" class="name">
          <label for="pin">PIN:</label>
        </td>
        <td valign="top" class="value ${hasErrors(bean:cmd,field:'pin','errors')}">
          <input type="text" id="pin" name="pin" value="${fieldValue(bean:cmd,field:'pin')}"/>
          (From game)
        </td>
      </tr>
      </tbody>
    </table>
    <td><input type="submit" value="Create user" /></td>
  </div>
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
