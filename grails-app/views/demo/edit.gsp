
<%@ page import="domain.demo.Demo" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Demo</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Demo List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Demo</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Demo</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${demoInstance}">
            <div class="errors">
                <g:renderErrors bean="${demoInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${demoInstance?.id}" />
                <input type="hidden" name="version" value="${demoInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="description">Description:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:demoInstance,field:'description','errors')}">
                                    <input type="text" id="description" name="description" value="${fieldValue(bean:demoInstance,field:'description')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="name">Name:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:demoInstance,field:'name','errors')}">
                                    <input type="text" id="name" name="name" value="${fieldValue(bean:demoInstance,field:'name')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tags">Tags:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:demoInstance,field:'tags','errors')}">
                                    
<ul>
<g:each var="t" in="${demoInstance?.tags?}">
    <li><g:link controller="demoTagRel" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="demoTagRel" params="['demo.id':demoInstance?.id]" action="create">Add DemoTagRel</g:link>

                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="user">User:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:demoInstance,field:'user','errors')}">
                                    <g:select optionKey="id" from="${domain.security.JsecUser.list()}" name="user.id" value="${demoInstance?.user?.id}" ></g:select>
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
