<#assign
    know = Session.SPRING_SECURITY_CONTEXT??
>

<#if know>
    <#assign
        currentUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = currentUser.getUsername()
        isAdmin = currentUser.isAdmin()
        isModerator = currentUser.isModerator()
        currentID = currentUser.getId()
    >
<#else>
    <#assign
        name = "unknown"
        isAdmin = false
        isModerator = false
        currentID = -1
    >
</#if>