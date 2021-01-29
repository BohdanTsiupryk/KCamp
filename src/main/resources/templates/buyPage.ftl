<#import "parts/common.ftl" as common>

<@common.page>
    <div class="form-group row">Сторінка покупки путівки у табір "${camp.nameCamp}"</div>
    <div class="form-group row">Зміна №${change.number} з ${change.beginDate} по ${change.endDate} </div>
    <form name="buyForm" action="/purpose" method="post" enctype="multipart/form-data">
        <input type="hidden" name="campId" value="${camp.getId()}">
        <input type="hidden" name="changeId" value="${change.getId()}">
        <div class="form-group row">
            <label for="inputEmail3" class="col-sm-2 col-form-label">ПБІ батьків</label>
            <div class="col-sm-10">
                <input  type="text" class="form-control" name="fullParentName" value="${user.fullName?if_exists}" />
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label>Дата народження</label>
                <input  type="date" class="form-control" name="birthday" value="${user.birthday?if_exists}" />

            </div>
            <div class="form-group col-md-3">
                <label>Серія та номер паспорта</label>
                <input  type="text" class="form-control" name="passportNumber" value="${user.passportNumber?if_exists}" />
            </div>
            <div class="form-group col-md-3">
                <label>Телефон</label>
                <input  type="number" class="form-control" name="phone" value="${user.phone?if_exists}" />
            </div>
            <div class="form-group col-md-3">
                <label>Громадянство</label>
                <input  type="text" class="form-control" name="citizenship" value="${user.citizenship?if_exists} /">
            </div>
        </div>
        <div class="form-group row">
            <label for="inputEmail3" class="col-sm-2 col-form-label">Місто проживання</label>
            <div class="col-sm-10">
                <input  type="text" class="form-control" name="city" value="${user.city?if_exists}" />
            </div>
        </div>
        <div class="form-group row">
            <label for="inputEmail3" class="col-sm-2 col-form-label">Адреса проживання</label>
            <div class="col-sm-10">
                <input  type="text" class="form-control" name="address" value="${user.address?if_exists}" />
            </div>
        </div>
        <br>
        <div class="form-group row">
            <label for="inputEmail3" class="col-sm-2 col-form-label">ПБІ дитини</label>
            <div class="col-sm-10">
                <input  type="text" class="form-control" name="kidFullName" placeholder="ПБІ дитини" />
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-3">
                <label>Свідоцтво про народження</label>
                <input required  type="text" class="form-control" name="kidDocument" placeholder="Серія та номер свідоцтва про народження" />
            </div>
            <div class="form-group col-md-3">
                <label>Дата народження</label>
                <input  required type="date" class="form-control" name="kidBirthday" placeholder="Дата народження" />
            </div>
            <div class="form-group col-md-3">
                <label>Громадянство</label>
                <input required type="text" class="form-control" name="kidCitizenship" placeholder="Громадянство" />
            </div>
            <div class="form-group col-md-3">
                <label>Місто проживання</label>
                <input required type="text" class="form-control" name="kidCity" placeholder="Місто проживання" />
            </div>
        </div>

        <div class="form-group row">
            <label for="inputEmail3" class="col-sm-2 col-form-label">Адреса проживання</label>
            <div class="col-sm-10">
                <input required type="text" class="form-control" name="kidAddress" placeholder="Адреса проживання" />
            </div>
        </div>
        <div class="form-group row">
            <label for="inputEmail3" class="col-sm-2 col-form-label">Місце навчання</label>
            <div class="col-sm-10">
                <input required type="text" class="form-control" name="kidName" placeholder="Місце навчання" />
            </div>
        </div>
        <div class="form-group row">
            <label for="inputEmail3" class="col-sm-2 col-form-label">Медичні особливості дитини, побажання</label>
            <div class="col-sm-10">
                <input required type="text" class="form-control" name="kidSpecialWishes" placeholder="Медичні особливості дитини, побажання" />
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-success">Купити</button>
    </form>
</@common.page>