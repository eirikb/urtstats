

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Player List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Player</g:link></span>
        </div>
        <div class="body">
            <h1>Player List</h1>
            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>

                            <g:sortableColumn property="id" title="Id" />

                            <g:sortableColumn property="nick" title="Nick" />

                            <g:sortableColumn property="ip" title="Ip" />

                            <g:sortableColumn property="level" title="Level" />

                            <g:sortableColumn property="exp" title="Exp" />

                            <g:sortableColumn property="nextlevel" title="Nextlevel" />

                        </tr>
                    </thead>
                    <tbody>
                        <g:each in="${playerInstanceList}" status="i" var="playerInstance">
                            <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">

                                <td><g:link action="show" id="${playerInstance.id}">${fieldValue(bean:playerInstance, field:'id')}</g:link></td>

                                <td>${fieldValue(bean:playerInstance, field:'nick')}</td>

                                <td>${fieldValue(bean:playerInstance, field:'ip')}</td>

                                <td>${fieldValue(bean:playerInstance, field:'level')}</td>

                                <td>${fieldValue(bean:playerInstance, field:'exp')}</td>

                                <td>${fieldValue(bean:playerInstance, field:'nextlevel')}</td>

                            </tr>
                        </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${playerInstanceTotal}" />
            </div>
        </div>
    </body>
</html>
