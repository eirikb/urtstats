

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>ForumPost List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New ForumPost</g:link></span>
        </div>
        <div class="body">
            <h1>ForumPost List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="subject" title="Subject" />
                        
                   	        <g:sortableColumn property="body" title="Body" />
                        
                   	        <th>Topic</th>
                   	    
                   	        <th>User</th>
                   	    
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${forumPostInstanceList}" status="i" var="forumPostInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${forumPostInstance.id}">${fieldValue(bean:forumPostInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:forumPostInstance, field:'subject')}</td>
                        
                            <td>${fieldValue(bean:forumPostInstance, field:'body')}</td>
                        
                            <td>${fieldValue(bean:forumPostInstance, field:'topic')}</td>
                        
                            <td>${fieldValue(bean:forumPostInstance, field:'user')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${forumPostInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
