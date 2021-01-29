<#import "parts/common.ftl" as common>
<#import "parts/changeReport.ftl" as rep>

<@common.page>
    ЗВІТ ${campName}
    <div>
        <#if isCampReport>
            <div>
                <ul class="list-group list-group-flush">
                    <li class="list-group-item"><h3><b>Теги</b></h3>
                        <#list report.locations as loc>
                            <span class="badge badge-light">${loc.getDescription()}</span>
                        </#list>
                        <#list report.interests as loc>
                            <span class="badge badge-info">${loc.getDescription()}</span>
                        </#list>
                        <#list report.childhoods as loc>
                            <span class="badge badge-dark">${loc.getDescription()}</span>
                        </#list>
                    </li>
                </ul>
            </div>
            <div>
                <#list report.campInfo as key, value>
                    <h5>${key} <span class="badge badge-secondary">${value}</span></h5>
                </#list>
            </div>
            <#list report.changeReportList as changeRep>
                <hr>
                <@rep.diagram changeRep.userCity "city"/>
                <@rep.diagram changeRep.childAge "age"/>
                <div class="container">
                    <div class="row">
                        <div class="col-sm">
                            <div id="city"></div>
                        </div>
                        <div class="col-sm">
                            <div id="age"></div>
                        </div>
                    </div>
                </div>
                <@rep.report changeRep/>
            </#list>
        <#else>
            <@rep.diagram report.userCity "city"/>
            <@rep.diagram report.childAge "age"/>
            <div class="container">
                <div class="row">
                    <div class="col-sm">
                        <div id="city" class="w-50 p-3" ></div>
                    </div>
                    <div class="col-sm">
                        <div id="age" class="w-50 p-3"></div>
                    </div>
                </div>
            </div>
            <@rep.report report/>
        </#if>
    </div>
</@common.page>