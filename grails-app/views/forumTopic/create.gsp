

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create ForumTopic</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ForumTopic List</g:link></span>
        </div>
        <div class="body">
            <h1>Create ForumTopic</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${forumTopicInstance}">
            <div class="errors">
                <g:renderErrors bean="${forumTopicInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumTopicInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:forumTopicInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="genre">Genre:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumTopicInstance,field:'genre','errors')}">
                                    <g:select optionKey="id" from="${ForumGenre.list()}" name="genre.id" value="${forumTopicInstance?.genre?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user">User:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumTopicInstance,field:'user','errors')}">
                                    <g:select optionKey="id" from="${JsecUser.list()}" name="user.id" value="${forumTopicInstance?.user?.id}" ></g:select>
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
