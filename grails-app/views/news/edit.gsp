

<html>
  <head>
  <resource:richTextEditor />
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="layout" content="main" />
  <title>Edit News</title>
</head>
<body>
  <div class="body">
    <h1>Edit News</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${news}">
      <div class="errors">
        <g:renderErrors bean="${news}" as="list" />
      </div>
    </g:hasErrors>
    <g:form method="post" >
      <input type="hidden" name="id" value="${news?.id}" />
      <input type="hidden" name="version" value="${news?.version}" />
      <div class="dialog">
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="title">Title:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:news,field:'title','errors')}">
                <input name="title" value="${fieldValue(bean:news, field:'title')}"/>
              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="head">Head:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:news,field:'head','errors')}">
          <richui:richTextEditor name="head" value="${fieldValue(bean:news,field:'head').decodeHTML()}" width="525" height="8" />
          </td>
          </tr>

          <tr class="prop">
            <td valign="top" class="name">
              <label for="body">Body:</label>
            </td>
            <td valign="top" class="value ${hasErrors(bean:news,field:'body','errors')}">
          <richui:richTextEditor name="body" value="${fieldValue(bean:news,field:'body').decodeHTML()}" width="525" />
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
