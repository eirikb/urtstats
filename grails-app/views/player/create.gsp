

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create Player</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Player List</g:link></span>
        </div>
        <div class="body">
            <h1>Create Player</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${playerInstance}">
            <div class="errors">
                <g:renderErrors bean="${playerInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nick">Nick:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'nick','errors')}">
                                    <input type="text" id="nick" name="nick" value="${fieldValue(bean:playerInstance,field:'nick')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="ip">Ip:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'ip','errors')}">
                                    <input type="text" id="ip" name="ip" value="${fieldValue(bean:playerInstance,field:'ip')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="level">Level:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'level','errors')}">
                                    <input type="text" id="level" name="level" value="${fieldValue(bean:playerInstance,field:'level')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="exp">Exp:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'exp','errors')}">
                                    <input type="text" id="exp" name="exp" value="${fieldValue(bean:playerInstance,field:'exp')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nextlevel">Nextlevel:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'nextlevel','errors')}">
                                    <input type="text" id="nextlevel" name="nextlevel" value="${fieldValue(bean:playerInstance,field:'nextlevel')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="challenge">Challenge:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'challenge','errors')}">
                                    <input type="text" id="challenge" name="challenge" value="${fieldValue(bean:playerInstance,field:'challenge')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="team">Team:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'team','errors')}">
                                    <g:select optionKey="id" from="${Team.list()}" name="team.id" value="${playerInstance?.team?.id}" noSelection="['null':'']"></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="login">Login:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'login','errors')}">
                                    <input type="text" id="login" name="login" value="${fieldValue(bean:playerInstance,field:'login')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="email">Email:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'email','errors')}">
                                    <input type="text" id="email" name="email" value="${fieldValue(bean:playerInstance,field:'email')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="createTime">Create Time:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'createTime','errors')}">
                                    <g:datePicker name="createTime" value="${playerInstance?.createTime}" precision="minute" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="deaths">Deaths:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'deaths','errors')}">
                                    <input type="text" id="deaths" name="deaths" value="${fieldValue(bean:playerInstance,field:'deaths')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="kills">Kills:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'kills','errors')}">
                                    <input type="text" id="kills" name="kills" value="${fieldValue(bean:playerInstance,field:'kills')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="urtID">Urt ID:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'urtID','errors')}">
                                    <input type="text" id="urtID" name="urtID" value="${fieldValue(bean:playerInstance,field:'urtID')}" />
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
