

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Show Player</title>
  </head>
  <body>
  <jsec:hasRole name="ADMIN">
    <div class="nav">
      <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="list" action="list">Player List</g:link></span>
      <span class="menuButton"><g:link class="create" action="create">New Player</g:link></span>
    </div>
  </jsec:hasRole>
  <div class="body">
    <h1>Show Player</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <div class="dialog">
      <table>
        <tbody>
          <tr class="prop">
            <td valign="top" class="name">Nick:</td>
            <td valign="top" class="value">${fieldValue(bean:playerInstance, field:'nick')}</td>
          </tr>
        <jsec:hasRole name="ADMIN">
          <tr class="prop">
            <td valign="top" class="name">Ip:</td>
            <td valign="top" class="value">${fieldValue(bean:playerInstance, field:'ip')}</td>
          </tr>
        </jsec:hasRole>

        <tr class="prop">
          <td valign="top" class="name">Level:</td>
          <td valign="top" class="value">${fieldValue(bean:playerInstance, field:'level')}</td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name">Exp:</td>
          <td valign="top" class="value">${fieldValue(bean:playerInstance, field:'exp')}</td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name">Nextlevel:</td>
          <td valign="top" class="value">${fieldValue(bean:playerInstance, field:'nextlevel')}</td>
        </tr>

        <jsec:hasRole name="ADMIN">
          <tr class="prop">
            <td valign="top" class="name">Chats:</td>

            <td  valign="top" style="text-align:left;" class="value">
              <ul>
                <g:each var="c" in="${playerInstance.chats}">
                  <li>${c?.message}</li>
                </g:each>
              </ul>
            </td>
          </tr>
        </jsec:hasRole>


        <tr class="prop">
          <td valign="top" class="name">Joined:</td>

          <td valign="top" class="value"><prettytime:display date="${playerInstance.getDateCreated()}" /></td>
        </tr>

        <tr class="prop">
          <td valign="top" class="name">Kills:</td>
          <td valign="top" class="value"></td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name">Deaths:</td>
          <td valign="top" class="value"></td>
        </tr>
        <tr class="prop">
          <td valign="top" class="name">Ratio:</td>
          <td valign="top" class="value"></td>
        </tr>

        </tbody>
      </table>
    </div>
    <jsec:hasRole name="ADMIN">
      <div class="buttons">
        <g:form>
          <input type="hidden" name="id" value="${playerInstance?.id}" />
          <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
          <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
        </g:form>
      </div>
    </jsec:hasRole>
  </div>
</body>
</html>
