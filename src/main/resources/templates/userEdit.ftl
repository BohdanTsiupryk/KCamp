<#include "parts/security.ftl">
<#import "parts/common.ftl" as common>

<@common.page>
    <p class="h1">Редагування інформації про користувача <i>${user.username}</i></p>

    <form action="/user" method="post">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1">ПБІ користувача</span>
            </div>
            <input type="text" class="form-control" name="fullName" value="${user.fullName}" />
        </div>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1">Адресса</span>
            </div>
            <input type="text" class="form-control" name="address" value="${user.address.address?if_exists}" />
        </div>
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <div class="form-group">
                        <label>Email</label><br>
                        <input type="text" class="form-control" name="email" value="${user.email?if_exists}" />
                    </div>
                </div>
                <div class="col-sm">
                    <div class="form-group">
                        <label>Номер телефону</label><br>
                        <input type="text" class="form-control" name="phone" value="${user.phone?if_exists}" />
                    </div>
                </div>
                <#if user.id == currentID>
                    <div class="col-sm">
                        <div class="form-group">
                            <label>Новий пароль</label><br>
                            <input type="password" name="password" class="form-control" placeholder="Новий пароль" />
                        </div>
                    </div>
                </#if>
            </div>
        </div>
        <div>
            <#if isAdmin>
                <#list roles as role>
                    <div class="form-check">
                        <input class="form-check-input" name="${role}" ${user.role?seq_contains(role)?string("checked", "")} type="checkbox" />
                        <label class="form-check-label" for="defaultCheck1">
                            ${role}
                        </label>
                    </div>
                </#list>
            </#if>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-sm">
                    <div class="form-group">
                        <label>День народження</label><br>
                        <input type="date" class="form-control" name="birthday" value="${user.userInfo.birthday?if_exists}" />
                    </div>
                </div>
                <div class="col-sm">
                    <div class="form-group">
                        <label>Паспортні дані</label><br>
                        <input type="text" class="form-control" name="passportNumber" value="${user.userInfo.passportNumber?if_exists}" />
                    </div>
                </div>
                <div class="col-sm">
                    <div class="form-group">
                        <label>Громадянство</label><br>
                        <input type="text" class="form-control" name="citizenship" value="${user.userInfo.citizenship?if_exists}" />
                    </div>
                </div>
                <div class="col-sm">
                    <div class="form-group">
                        <label>Місто проживання</label><br>
                        <input type="text" class="form-control" name="city" value="${user.address.city?if_exists}" />
                    </div>
                </div>
                <div class="col-sm">
                    <label>&nbsp;</label><br>
                    <button type="submit" class="btn btn-primary">Зберегти</button>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="username" value="${user.username}"/>
            <input type="hidden" name="id" value="${user.id}"/>
        </div>
    </form>
</@common.page>