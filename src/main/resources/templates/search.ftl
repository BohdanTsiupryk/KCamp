<#import "parts/common.ftl" as common>
<#import "parts/mapMac.ftl" as googleMap>
<#include "parts/security.ftl">

<@common.page>
    <@googleMap.multyMap coords apiKey/>

    <div class="modal-body"><div id="map"></div></div>

    <div class="card-columns">
        <#list camps as camp>
            <div class="card my-3" style="width: 18rem;">
                <div class="card-header">
                    <a href="/camp/profile/${camp.id}">${camp.nameCamp}</a>
                    <#if camp.rating != 0>Рейтинг ${camp.rating}/10 <#else> Ніхто не оцінив табір </#if>
                </div>
                <#if camp.mainPicName??>
                    <img class="card-img-top" src="${camp.mainPicName}" height="200">
                </#if>
                <div class="m-2">
                    <span class="card-text">
                        <#if (camp.description?length < 150)>
                            ${camp.description}
                        <#else>
                            ${camp.description?substring(0, 140)}<a href="/camp/profile/${camp.id}">...</a>
                        </#if>
                    </span>
                </div>
                <div class="card-footer text-muted">
                    ${camp.author.username}
                </div>
            </div>
        </#list>
    </div>
</@common.page>
