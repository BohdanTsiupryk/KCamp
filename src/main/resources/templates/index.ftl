<#import "parts/common.ftl" as common>

<@common.page>
    <div class="mt-3">
        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#filter" aria-expanded="false" aria-controls="collapseExample">
            Фільтр
        </button>
        <a class="btn btn-primary" href="/sort" role="button">Відсортувати по рейтингу</a>
    </div>
    <div class="collapse" id="filter">
        <form action="/filter" method="post" name="filter" enctype="multipart/form-data">
            <div class="form-row">
                <div class="col">
                    <label>Табірні особливості (Можна обрати декілька)</label>
                    <select multiple="multiple" class="form-control" name="interests">
                        <#list interests as inter>
                            <option value="${inter}">${inter.getDescription()}</option>
                        </#list>
                    </select>
                </div>
                <div class="col">
                    <label>Розташування (Можна обрати декілька)</label>
                    <select multiple="multiple" class="form-control" name="locations">
                        <#list locations as location>
                            <option value="${location}">${location.getDescription()}</option>
                        </#list>
                    </select>
                </div>
                <div class="col">
                    <label>Вікова категорія (Можна обрати декілька)</label>
                    <select multiple="multiple" class="form-control" name="locations">
                        <#list childhoods as hood>
                            <option value="${hood}">${hood.getDescription()}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-primary" type="submit">Фільтрувати</button>
        </form>
    </div>
    <div class="card-columns">
        <#list camps as camp>
            <div class="card my-3" style="width: 18rem;">
                <div class="card-header">
                    <a href="/camp/profile/${camp.id}">${camp.nameCamp}</a>
                    <#if camp.rating != 0>Рейтинг ${camp.rating}/10 <#else> Ніхто не оцінив табір </#if>
                </div>
                <#if camp.mainPicName??>
                    <img class="card-img-top" src="/img/${camp.mainPicName}" height="200">
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
                <div  class="card-footer text-muted">
                    ${camp.author.username}
                </div>
            </div>
        </#list>
    </div>
</@common.page>
