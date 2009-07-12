

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show ForumPost</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ForumPost List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New ForumPost</g:link></span>
        </div>
        <div class="body">
            <h1>Show ForumPost</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:forumPostInstance, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Subject:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:forumPostInstance, field:'subject')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Body:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:forumPostInstance, field:'body')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Topic:</td>
                            
                            <td valign="top" class="value"><g:link controller="forumTopic" action="show" id="${forumPostInstance?.topic?.id}">${forumPostInstance?.topic?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">User:</td>
                            
                            <td valign="top" class="value"><g:link controller="jsecUser" action="show" id="${forumPostInstance?.user?.id}">${forumPostInstance?.user?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${forumPostInstance?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
