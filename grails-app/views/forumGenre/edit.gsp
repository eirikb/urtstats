

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit ForumGenre</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ForumGenre List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New ForumGenre</g:link></span>
        </div>
        <div class="body">
            <h1>Edit ForumGenre</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${forumGenreInstance}">
            <div class="errors">
                <g:renderErrors bean="${forumGenreInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${forumGenreInstance?.id}" />
                <input type="hidden" name="version" value="${forumGenreInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumGenreInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:forumGenreInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="topics">Topics:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:forumGenreInstance,field:'topics','errors')}">
                                    
<ul>
<g:each var="t" in="${forumGenreInstance?.topics?}">
    <li><g:link controller="forumTopic" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="forumTopic" params="['forumGenre.id':forumGenreInstance?.id]" action="create">Add ForumTopic</g:link>

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
