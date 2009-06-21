<html>
    <head>
        <title><g:layoutTitle default="Grddails" /></title>
        <link rel="stylesheet" href="${resource(dir:'css',file:'main.css')}" />
        <link rel="stylesheet" href="${resource(dir:'css',file:'stylesheet.css')}" />
        <link rel="shortcut icon" href="${resource(dir:'images',file:'favicon.ico')}" type="image/x-icon" />
        <g:layoutHead />
        <g:javascript library="application" />
    </head>
    <body>
        <div id="spinner" class="spinner" style="display:none;">
            <img src="${resource(dir:'images',file:'spinner.gif')}" alt="Spinner" />
        </div>
        <div id="container">
            <img src="${resource(dir:'images',file:'ut.png')}" width="100px" height="100px" alt="Grails" />
            <ul class="tabs" id="tabnav">
                <li><a href="${resource(dir:'')}">Home</a></li>
                <g:each var="c" in="${grailsApplication.controllerClasses}">
                    <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
                </g:each>
            </ul>
            <g:layoutBody />
        </div>
    </body>
</html>