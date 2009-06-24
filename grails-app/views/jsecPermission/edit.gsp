

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit JsecPermission</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">JsecPermission List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New JsecPermission</g:link></span>
        </div>
        <div class="body">
            <h1>Edit JsecPermission</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${jsecPermissionInstance}">
            <div class="errors">
                <g:renderErrors bean="${jsecPermissionInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${jsecPermissionInstance?.id}" />
                <input type="hidden" name="version" value="${jsecPermissionInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="type">Type:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:jsecPermissionInstance,field:'type','errors')}">
                                    <input type="text" id="type" name="type" value="${fieldValue(bean:jsecPermissionInstance,field:'type')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="possibleActions">Possible Actions:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:jsecPermissionInstance,field:'possibleActions','errors')}">
                                    <input type="text" id="possibleActions" name="possibleActions" value="${fieldValue(bean:jsecPermissionInstance,field:'possibleActions')}"/>
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
