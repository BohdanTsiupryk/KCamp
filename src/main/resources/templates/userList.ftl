<#include "parts/security.ftl">
<#import "parts/common.ftl" as common>

<@common.page>
    <#if isAdmin>
    <div class="mb-2"> <h2>Список користувачів</h2></div>

    <table class="table">
        <tr>
            <th scope="col">Логін</th>
            <th scope="col">Номер телефону</th>
            <th scope="col">Електронна пошта</th>
            <th scope="col">Ролі</th>
            <th></th>
            <th></th>
        </tr>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <td>${user.phone?if_exists}</td>
                <td>${user.email?if_exists}</td>
                <td><#list user.role as role>${role}<#sep>, </#list></td>
                <td><a href="/user/editor/${user.id}">Редагувати</a></td>
                <td><a href="/user/delete/${user.id}">Видалити</a></td>
            </tr>
        </#list>
    </table>
    </#if>
</@common.page>
