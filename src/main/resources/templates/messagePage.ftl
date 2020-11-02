<#import "parts/common.ftl" as common>

<@common.page>
<div>
    ${message?if_exists}
    ${form?if_exists}
</div>
</@common.page>