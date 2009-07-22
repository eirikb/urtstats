

<html>
  <head>
  <resource:richTextEditor />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Profile</title>
</head>
<body>
  <div class="body">
    <h1>Profile</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${user}">
      <div class="errors">
        <g:renderErrors bean="${user}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" >
      <input type="hidden" name="id" value="${user?.id}" />
      <input type="hidden" name="version" value="${user?.version}" />
      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="username">Username: ${fieldValue(bean:user, field:'username')}</label>
              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="email">Email:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:user, field:'email','errors')}">
                <input name="email" value="${fieldValue(bean:user, field:'email')}"/>
              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="firstname">First name:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:user, field:'firstname','errors')}">
                <input name="firstname" value="${fieldValue(bean:user, field:'firstname')}"/>
              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="lastname">Last name:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:user, field:'lastname','errors')}">
                <input name="lastname" value="${fieldValue(bean:user, field:'lastname')}"/>
              </td>
            </tr>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><g:actionSubmit class="save" value="Update" /></span>
        <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
