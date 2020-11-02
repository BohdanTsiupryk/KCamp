<#include "security.ftl">

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Camps</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Домашня</a>
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/user">Список користувачів</a>
                </li>
            </#if>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/request">Запити</a>
                </li>
            </#if>
            <li class="nav-item">
                <a class="nav-link" href="/profile">Профіль</a>
            </li>
        </ul>
        <#if name=="unknown">
            <biv class="nav-item">
                <a class="nav-link" href="/login">Увійти</a>
            </biv>
        <#else>
            <div class="nav-item">
                <form action="/logout" method="post">
                    <button type="submit" class="btn btn-primary mr-2" >Вийти</button>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                </form>
            </div>
        </#if>
        <div class="navbar-text">
            ${name}
        </div>
    </div>
</nav>