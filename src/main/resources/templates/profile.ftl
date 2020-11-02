<#import "parts/orders.ftl" as or>
<#include "parts/security.ftl">
<#import "parts/common.ftl" as common>

<@common.page>
    <p class="h3">Привіт, <i>${userFormDb.username}</i></p>
    <#if message??>
    <div class="alert alert-success" role="alert">
        ${message}
    </div>
    </#if>
    <#if modReq??>
        <div>
            <div class="alert alert-success" role="alert">
                Ваш запит успішно надіслано, протягом 24 годин він буде розглянутий.
            </div>
        </div>
    </#if>
    <#if userFormDb.isModerator()>
        <p>
            <a class="btn btn-primary" data-toggle="collapse" href="#formAddCamp" role="button" aria-expanded="false" aria-controls="collapseExample">
                Додати табір
            </a>
        </p>
        <div class="collapse <#if campError>show</#if>" id="formAddCamp">
            <form action="/camp/add" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label>Назва табору</label>
                    <input type="text" name="nameCamp" placeholder="Назва табору"  class="form-control ${(nameCampError??)?string('is-invalid','')}"
                           <#if camp??>value="${camp.nameCamp?if_exists}" </#if>/>
                    <div class="invalid-feedback">
                        ${nameCampError?if_exists}
                    </div>
                </div>
                <div class="form-group">
                    <label>Адреса табору</label>
                    <input type="text" name="address" placeholder="Адреса табору"  class="form-control ${(addressError??)?string('is-invalid','')}"
                           <#if camp??>value="${camp.address?if_exists}" </#if>/>
                    <div class="invalid-feedback">
                        ${addressError?if_exists}
                    </div>
                </div>
                <div class="form-group">
                    <label>Опис</label>
                    <textarea class="form-control ${(descriptionError??)?string('is-invalid','')}" placeholder="Опис" name="description" ><#if camp??>${camp.description?if_exists}</#if></textarea>
                    <div class="invalid-feedback">
                        ${descriptionError?if_exists}
                    </div>
                </div>
                <div class="form-row">
                    <div class="col">
                        <label>Табірні особливості (Можна обрати декілька)</label>
                        <select  multiple="multiple" class="form-control" name="interestsf" required="required">
                            <#list interests as inter>
                                <option value="${inter.getName()}">${inter.getName()}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="col">
                        <label>Локація табору</label>
                        <select  multiple="multiple" class="form-control" name="locationsf" required="required">
                            <#list locations as local>
                                <option value="${local.getName()}">${local.getName()}</option>
                            </#list>
                        </select>
                    </div>
                    <div class="col">
                        <label>Вікова категорія табору</label>
                        <select  multiple="multiple" class="form-control" name="childhoodsf" required="required">
                            <#list childhoods as hood>
                                <option value="${hood}">${hood.getDescription()}</option>
                            </#list>
                        </select>
                    </div>
                </div>
                <div class="custom-file mt-3">
                    <input type="file" class="image" id="customFile" name="image" required="required"/>
                    <label class="custom-file-label" for="customFile">Оберіть фото</label>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <div class="my-3">
                <input type="submit" value="Додати" class="btn btn-primary"/>
                </div>
            </form>
        </div>
    </#if>
    <div class="container">
        <div class="row">
            <div class="col-12 col-md-8">
                <#if isModerator>
                <table class="table">
                    <tr>
                        <th scope="col">Назва табору</th>
                        <th scope="col">Опис</th>
                        <th scope="col"></th>
                        <th></th>
                        <th></th>
                    </tr>

                    <#list userCamps as camp>
                        <tr>
                            <td><a href="/camp/profile/${camp.id}">${camp.nameCamp}</a></td>
                            <td>
                                <#if (camp.description?length < 150)>
                                    ${camp.description}
                                <#else>
                                    ${camp.description?substring(0, 140)}<a href="/camp/profile/${camp.id}">...</a>
                                </#if>
                            </td>
                            <td>
                                <#if camp.mainPicName??>
                                    <img class="rounded float-left" src="/img/${camp.mainPicName}" width="200" height="200">
                                </#if>
                            </td>
                            <td><a href="/camp/${camp.id}">Редагувати</a></td>
                            <td>
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modalCamp${camp.id}">
                                    Видалити
                                </button>
                            </td>
                        </tr>
                        <div class="modal fade" id="modalCamp${camp.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                            <div class="modal-dialog modal-dialog-centered" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="modalCamp${camp.id}">Підтвердження видалення</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        Ви справді бажаєте видалити табір <p><i>${camp.nameCamp}</i></p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Скасувати</button>
                                        <a class="btn btn-primary" href="/camp/delete/${camp.id}" role="button">Видалити</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#list>
                </table>
                    <#else>
                     <@or.list userFormDb.boughtTrips/>
                </#if>
            </div>
            <div class="col-6 col-md-4">
                <#if userFormDb.getActivationCode() != "">
                    <div class="alert alert-success" role="alert">
                        Будь ласка, активуйте свій акаунт!
                    </div>
                </#if>
                <div class="row mb-3">
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="/user/editor/${currentID}">Редагувати дані</a></li>
                            <li class="breadcrumb-item"><a data-toggle="collapse" href="#detail">Деталі</a></li>
                            <li class="breadcrumb-item"><a href="/user/orders">Куплені путівки</a></li>
                        </ol>
                    </nav>
                </div>
                <h4> Логін : <span class="badge badge-secondary">${userFormDb.username}</span></h4>
                <h4> ПБІ : <span class="badge badge-secondary">${userFormDb.fullName}</span></h4>
                <h4> Електронна пошта : <span class="badge badge-secondary">${userFormDb.email}</span></h4>
                <h4> Телефон : <span class="badge badge-secondary">${userFormDb.phone}</span></h4>
                <h4> Роль :
                    <span class="badge badge-secondary">
                        <#list userFormDb.getRole() as role>
                            ${role.name()}<#sep>,
                        </#list>
                    </span>
                </h4>

                <div class="collapse" id="detail">
                    <h4> День народження : <span class="badge badge-secondary">${userFormDb.userInfo.getBirthday()?if_exists}</span></h4>
                    <h4> Національність : <span class="badge badge-secondary">${userFormDb.userInfo.getCitizenship()?if_exists}</span></h4>
                    <h4> Серія та номер паспорта : <span class="badge badge-secondary">${userFormDb.userInfo.getPassportNumber()?if_exists}</span></h4>
                    <h4> Місто проживання : <span class="badge badge-secondary">${userFormDb.address.city?if_exists}</span></h4>
                    <h4> Адресса : <span class="badge badge-secondary">${userFormDb.address.address?if_exists}</span></h4>
                </div>
            </div>
        </div>
    </div>
</@common.page>
