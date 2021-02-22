<#import "parts/common.ftl" as common>

<@common.page>
<div>
    ${message?if_exists}

    <dl class="row">
        <dt class="col-sm-3">Найменування табору</dt>
        <dd class="col-sm-9">${data.get("camp name")?if_exists}</dd>

        <dt class="col-sm-3">Ім'я замовника</dt>
        <dd class="col-sm-9">${data.get("user name")?if_exists}</dd>

        <dt class="col-sm-3">Дата початку</dt>
        <dd class="col-sm-9">${data.get("begin date")?if_exists}</dd>

        <dt class="col-sm-3">Дата кінця</dt>
        <dd class="col-sm-9">${data.get("end date")?if_exists}</dd>

        <dt class="col-sm-3">Спеціальні побажання</dt>
        <dd class="col-sm-9">${data.get("kidSpecialWishes")?if_exists}</dd>

        <dt class="col-sm-3">Інформація про дитину:</dt>

        <dt class="col-sm-3">Ім'я</dt>
        <dd class="col-sm-9">${data.get("kidFullName")?if_exists}</dd>

        <dt class="col-sm-3">Місто проживання</dt>
        <dd class="col-sm-9">${data.get("kidCity")?if_exists}</dd>

        <dt class="col-sm-3">Адреса проживання</dt>
        <dd class="col-sm-9">${data.get("kidAddress")?if_exists}</dd>

        <dt class="col-sm-3">Номер документа</dt>
        <dd class="col-sm-9">${data.get("kidDocument")?if_exists}</dd>

        <dt class="col-sm-3">День народження</dt>
        <dd class="col-sm-9">${data.get("kidBirthday")?if_exists}</dd>

        <dt class="col-sm-3">Громадянство</dt>
        <dd class="col-sm-9">${data.get("kidCitizenship")?if_exists}</dd>

    </dl>
</div>
</@common.page>