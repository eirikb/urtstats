

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit ForumPost</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ForumPost List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New ForumPost</g:link></span>
        </div>
        <div class="body">
            <h1>Edit ForumPost</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${forumPostInstance}">
            <div class="errors">
                <g:renderErrors bean="${forumPostInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${forumPostInstance?.id}" />
                <input type="hidden" name="version" value="${forumPostInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="subject">Subject:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumPostInstance,field:'subject','errors')}">
                                    <input type="text" id="subject" name="subject" value="${fieldValue(bean:forumPostInstance,field:'subject')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="body">Body:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumPostInstance,field:'body','errors')}">
                                    <input type="text" id="body" name="body" value="${fieldValue(bean:forumPostInstance,field:'body')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="topic">Topic:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumPostInstance,field:'topic','errors')}">
                                    <g:select optionKey="id" from="${ForumTopic.list()}" name="topic.id" value="${forumPostInstance?.topic?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user">User:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumPostInstance,field:'user','errors')}">
                                    <g:select optionKey="id" from="${JsecUser.list()}" name="user.id" value="${forumPostInstance?.user?.id}" ></g:select>
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
