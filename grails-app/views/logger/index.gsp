

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Player</title>
    </head>
    <body>
        <div class="body">
            <h1>Run!</h1>
            ${parser}

            <g:if test="${flash.message}">
                <div class="message">${flash.message}</div>
            </g:if>
        </div>
    </body>
</html>
