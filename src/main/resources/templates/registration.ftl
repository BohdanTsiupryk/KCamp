<#import "parts/common.ftl" as common>

<@common.page>
    <div class="mb-2"><h4>Форма реєстрації</h4></div>
    <#if message??>
        <div class="alert alert-danger" role="alert">
            ${message}
        </div>
    </#if>
    <form action="/registration" method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Логін : </label>
            <div class="col-sm-6">
                <input type="text" class="form-control ${(usernameError??)?string('is-invalid','')}"
                       <#if user??>value="${user.username?if_exists}"</#if> name="username" placeholder="Логін" />
                <div class="invalid-feedback">
                    ${usernameError?if_exists}
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Логін : </label>
            <div class="col-sm-6">
                <input type="text" class="form-control ${(fullNameError??)?string('is-invalid','')}"
                       <#if user??>value="${user.fullName?if_exists}"</#if> name="fullName" placeholder="ПБІ користувача" />
                <div class="invalid-feedback">
                    ${fullNameError?if_exists}
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Телефон : </label>
            <div class="col-sm-6">
                <input type="text" class="form-control ${(phoneError??)?string('is-invalid','')}"
                       <#if user??>value="${user.phone?if_exists}"</#if> name="phone" placeholder="Телефон"/>
                <div class="invalid-feedback">
                    ${phoneError?if_exists}
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Електронна пошта : </label>
            <div class="col-sm-6">
                <input type="email" class="form-control ${(emailError??)?string('is-invalid','')}"
                       <#if user??>value="${user.email?if_exists}"</#if> name="email" placeholder="Email"/>
                <div class="invalid-feedback">
                    ${emailError?if_exists}
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Пароль : </label>
            <div class="col-sm-6">
                <input type="password" class="form-control ${(passwordError?? || passPowerError??)?string('is-invalid','')}"
                       name="password" placeholder="Пароль"/>
                <div class="invalid-feedback">
                    ${passwordError?if_exists}
                </div>
                <div class="invalid-feedback">
                    ${passPowerError?if_exists}
                </div>
            </div>
        </div>
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Підтвердження паролю : </label>
            <div class="col-sm-6">
                <input type="password" class="form-control ${(password2Error??)?string('is-invalid','')}"
                       name="password2" placeholder="Підтвердження паролю"/>
                <div class="invalid-feedback">
                    ${password2Error?if_exists}
                </div>
            </div>
        </div>
        <div class="col-sm-10">
            <button type="submit" class="btn btn-primary">Зареєструватись</button>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    </form>
</@common.page>
