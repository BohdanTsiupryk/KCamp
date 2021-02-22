<#import "parts/common.ftl" as common>

<@common.page>
<div>
    ${message?if_exists}

    <dl class="row">
        <dt class="col-sm-3">Найменування табору</dt>
        <dd class="col-sm-9">${camp_name?if_exists}</dd>

        <dt class="col-sm-3">Ім'я замовника</dt>
        <dd class="col-sm-9">${user_name?if_exists}</dd>

        <dt class="col-sm-3">Дата початку</dt>
        <dd class="col-sm-9">${begin_date?if_exists}</dd>

        <dt class="col-sm-3">Дата кінця</dt>
        <dd class="col-sm-9">${end_date?if_exists}</dd>

        <dt class="col-sm-3">Спеціальні побажання</dt>
        <dd class="col-sm-9">${kidSpecialWishes?if_exists}</dd>

        <dt class="col-sm-3">Інформація про дитину:</dt>

        <dt class="col-sm-3">Ім'я</dt>
        <dd class="col-sm-9">${kidFullName?if_exists}</dd>

        <dt class="col-sm-3">Місто проживання</dt>
        <dd class="col-sm-9">${kidCity?if_exists}</dd>

        <dt class="col-sm-3">Адреса проживання</dt>
        <dd class="col-sm-9">${kidAddress?if_exists}</dd>

        <dt class="col-sm-3">Номер документа</dt>
        <dd class="col-sm-9">${kidDocument?if_exists}</dd>

        <dt class="col-sm-3">День народження</dt>
        <dd class="col-sm-9">${kidBirthday?if_exists}</dd>

        <dt class="col-sm-3">Громадянство</dt>
        <dd class="col-sm-9">${kidCitizenship?if_exists}</dd>

    </dl>
</div>
</@common.page>