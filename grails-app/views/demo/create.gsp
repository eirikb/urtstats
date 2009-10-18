
<%@ page import="domain.demo.Demo" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Create Demo</title>
  <resource:richTextEditor />
</head>
<body>
  <div class="nav">
    <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
    <span class="menuButton"><g:link class="list" action="list">Demo List</g:link></span>
  </div>
  <div class="body">
    <h1>Create Demo</h1>
    <g:if test="${flash.message}">
      <div class="message">${flash.message}</div>
    </g:if>
    <g:hasErrors bean="${demoInstance}">
      <div class="errors">
        <g:renderErrors bean="${demoInstance}" as="list" />
      </div>
    </g:hasErrors>
    <g:form action="save" method="post" enctype="multipart/form-data" >
      <div class="dialog">
        <input type="file" name="file"/>
        <table>
          <tbody>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="name">Name:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:demoInstance,field:'name','errors')}">
                <input type="text" id="name" name="name" value="${fieldValue(bean:demoInstance,field:'name')}"/>
              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="description">Description:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:demoInstance,field:'description','errors')}">
          <richui:richTextEditor name="description" value="${fieldValue(bean:demoInstance,field:'description').decodeHTML()}" width="525" height="8" />
          </td>
          </tr>

          </tbody>
        </table>
      </div>
      <div class="buttons">
        <span class="button"><input class="save" type="submit" value="Create" /></span>
      </div>
    </g:form>
  </div>
</body>
</html>
