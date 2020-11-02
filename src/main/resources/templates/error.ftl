<#import "parts/common.ftl" as common>

<@common.page>

    <h1>Error Page</h1>
    <div>Status code: ${statusCode?if_exists}</div>
    <div>Exception message: ${exception?if_exists}</div>

</@common.page>