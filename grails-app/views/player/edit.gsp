

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="main" />
    <title>Edit Player</title>
  </head>
  <body>
    <div class="nav">
      <span class="menuButton"><a class="home" href="${resource(dir:'')}">Home</a></span>
      <span class="menuButton"><g:link class="list" action="list">Player List</g:link></span>
      <span class="menuButton"><g:link class="create" action="create">New Player</g:link></span>
    </div>
    <div class="body">
      <h1>Edit Player</h1>
      <g:if test="${flash.message}">
        <div class="message">${flash.message}</div>
      </g:if>
      <g:hasErrors bean="${playerInstance}">
        <div class="errors">
          <g:renderErrors bean="${playerInstance}" as="list" />
        </div>
      </g:hasErrors>
      <g:form method="post" >
        <input type="hidden" name="id" value="${playerInstance?.id}" />
        <input type="hidden" name="version" value="${playerInstance?.version}" />
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
                <label for="items">Items:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'items','errors')}">

              </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="chats">Chats:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'chats','errors')}">

                <ul>
                  <g:each var="c" in="${playerInstance?.chats?}">
                    <li><g:link controller="chat" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                  </g:each>
                </ul>
            <g:link controller="chat" params="['player.id':playerInstance?.id]" action="create">Add Chat</g:link>

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
                <label for="deathCauses">Death Causes:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'deathCauses','errors')}">

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
                <label for="hitters">Hitters:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'hitters','errors')}">

                <ul>
                  <g:each var="h" in="${playerInstance?.hitters?}">
                    <li><g:link controller="hit" action="show" id="${h.id}">${h?.encodeAsHTML()}</g:link></li>
                  </g:each>
                </ul>
            <g:link controller="hit" params="['player.id':playerInstance?.id]" action="create">Add Hit</g:link>

            </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="killeds">Killeds:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'killeds','errors')}">

                <ul>
                  <g:each var="k" in="${playerInstance?.killeds?}">
                    <li><g:link controller="kill" action="show" id="${k.id}">${k?.encodeAsHTML()}</g:link></li>
                  </g:each>
                </ul>
            <g:link controller="kill" params="['player.id':playerInstance?.id]" action="create">Add Kill</g:link>

            </td>
            </tr>

            <tr class="prop">
              <td valign="top" class="name">
                <label for="killers">Killers:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'killers','errors')}">

                <ul>
                  <g:each var="k" in="${playerInstance?.killers?}">
                    <li><g:link controller="kill" action="show" id="${k.id}">${k?.encodeAsHTML()}</g:link></li>
                  </g:each>
                </ul>
            <g:link controller="kill" params="['player.id':playerInstance?.id]" action="create">Add Kill</g:link>

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
                <label for="playerLogs">Player Logs:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'playerLogs','errors')}">

                <ul>
                  <g:each var="p" in="${playerInstance?.playerLogs?}">
                    <li><g:link controller="playerLog" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></li>
                  </g:each>
                </ul>
            <g:link controller="playerLog" params="['player.id':playerInstance?.id]" action="create">Add PlayerLog</g:link>

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

            <tr class="prop">
              <td valign="top" class="name">
                <label for="victims">Victims:</label>
              </td>
              <td valign="top" class="value ${hasErrors(bean:playerInstance,field:'victims','errors')}">

                <ul>
                  <g:each var="v" in="${playerInstance?.victims?}">
                    <li><g:link controller="hit" action="show" id="${v.id}">${v?.encodeAsHTML()}</g:link></li>
                  </g:each>
                </ul>
            <g:link controller="hit" params="['player.id':playerInstance?.id]" action="create">Add Hit</g:link>

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
