<g:hasErrors bean="${user?.errors}" field="username">
  <div class="errors">
    <g:renderErrors bean="${user}" field="username"/>
  </div>
</g:hasErrors>