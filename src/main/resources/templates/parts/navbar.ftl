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
        <form class="form-inline my-5 my-lg-0" action="/search" method="get" name="search" enctype="multipart/form-data">
            <input class="form-control mr-sm-2" name="query" type="text" placeholder="Ліс, підліток..." aria-label="Search">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Пошук</button>
        </form>
        <#if name=="unknown">
            <biv class="nav-item ml-5">
                <a class="nav-link" href="/login">Увійти</a>
            </biv>
        <#else>
            <div class="nav-item ml-5">
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