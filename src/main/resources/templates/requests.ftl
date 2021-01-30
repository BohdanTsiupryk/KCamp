<#import "parts/common.ftl" as common>

<@common.page>
    <h1><i>Запити</i></h1>
    <#if hasReq>
    <div>
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">ПБІ</th>
                <th scope="col">Назва табору</th>
                <th scope="col">URL</th>
                <th scope="col">Коментар</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#list requests as req>
              <tr>
                <th scope="row">${req.id}</th>
                <td>${req.fullName}</td>
                <td>${req.campName}</td>
                <td><a href="${req.campUrl}">${req.campUrl}</a></td>
                <td><#if req.message??>${req.message}<#else> - </#if></td>
                <td><a href="/user/editor/${req.author.id}">Редагувати</a></td>
                <td><a href="/request/delete/${req.id}">Видалити запит</a></td>
              </tr>
            </#list>
            </tbody>
        </table>
    </div>
    <#else>
        <h2>Поки запитів немає!</h2>
    </#if>
</@common.page>