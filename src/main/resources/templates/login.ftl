<#import "parts/common.ftl" as common>

<@common.page>
    <div class="mb-2">Форма входу</div>
    <#if message??>
        <div class="alert alert-${alert}" role="alert">
            ${message}
        </div>
    </#if>
    <form action="/login" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Логін : </label>
            <div class="col-sm-6">
                <input type="text" class="form-control" name="username" placeholder="User Name"/>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Пароль : </label>
            <div class="col-sm-6">
                <input type="password" class="form-control" name="password" placeholder="Password"/>
            </div>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div class="col-sm-10">
            <button type="submit" class="btn btn-primary">Увійти</button>
        </div>
    </form>
    <div>
        Не зареєстровані у системі? <a href="/registration">Зареєструватись тут</a>
    </div>
</@common.page>
