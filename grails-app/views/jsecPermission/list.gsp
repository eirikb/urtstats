

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>JsecPermission List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New JsecPermission</g:link></span>
        </div>
        <div class="body">
            <h1>JsecPermission List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="type" title="Type" />
                        
                   	        <g:sortableColumn property="possibleActions" title="Possible Actions" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${jsecPermissionInstanceList}" status="i" var="jsecPermissionInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${jsecPermissionInstance.id}">${fieldValue(bean:jsecPermissionInstance, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:jsecPermissionInstance, field:'type')}</td>
                        
                            <td>${fieldValue(bean:jsecPermissionInstance, field:'possibleActions')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${jsecPermissionInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
