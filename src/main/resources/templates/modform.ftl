<#import "parts/common.ftl" as common>

<@common.page>
<div class="mt-3">
    <form  action="/modrequest" method="post">
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1">ПБІ представника</span>
            </div>
            <input type="text" class="form-control" name="fullName" required placeholder="ПБІ" />
        </div>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1">Назва табору</span>
            </div>
            <input type="text" class="form-control" name="campName" required placeholder="Назва" />
        </div>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1">Посилання на сайт табору</span>
            </div>
            <input type="text" class="form-control" name="campUrl" required placeholder="URI" />
        </div>
        <div class="input-group mb-3">
            <div class="input-group-prepend">
                <span class="input-group-text" id="basic-addon1">Коментар</span>
            </div>
            <input type="text" class="form-control" name="comment" required placeholder="Коментар" />
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary" type="submit">Надіслати запит</button>
    </form>
</div>
</@common.page>