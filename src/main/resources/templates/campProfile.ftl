<#include "parts/security.ftl">
<#import "parts/common.ftl" as common>
<#import "parts/mapMac.ftl" as googleMap>

<@common.page>
    <@googleMap.map coords.latitude coords.longitude apiKey/>
    <div class="container">
        <div class="row">
            <div class="col-6">
                <img src="${camp.mainPicName}" alt="${camp.mainPicName}">
                <br>
                <#list camp.locations as location>
                    <a href="/filter/loc/${location}" class="badge badge-light">${location.getDescription()}</a>
                </#list>
                <br>
                <#list camp.childhoods as hood>
                    <a href="/filter/hood/${hood}" class="badge badge-dark">${hood.getDescription()}</a>
                </#list>
                <br>
                <#list camp.interesting as interest>
                    <a href="/filter/inter/${interest}" class="badge badge-info">${interest.getDescription()}</a>
                </#list>
                <br>
            </div>
            <div class="col-6">

            </div>
            <div class="w-100"></div>
            <div class="col-6">
                <p class="h4">Трішки про табір <b>${camp.nameCamp}</b> =)</p>
                <div>
                    ${camp.description}
                </div>
                <h5> Табір знаходиться за адресою <span class="badge badge-secondary">${camp.address}</span></h5>
                <h5> Контактний телефон<span class="badge badge-secondary">${camp.author.phone}</span></h5>
            </div>
            <div class="col-6">
                <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
                    <div class="carousel-inner">
                        <#list camp.campPhotos as photo>
                            <div class="carousel-item <#if photo_index==0>active</#if>">
                                <img src="${photo}" height="50%" class="d-block w-100" alt="${photo}" />
                            </div>
                        </#list>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                        <span class="sr-only">Попередній</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Наступний</span>
                    </a>
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col">
                <div class="mt-5">
                <p class="h4">Інформація про заїзди </p>
                    <#if (isModerator && camp.author.id == currentID)>
                        <div class="my-4">
                            <a class="btn btn-primary" href="/report/camp/${camp.id}" role="button">Звіт про табір</a>
                        </div>
                    </#if>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Зміна</th>
                        <th scope="col">Дата заїзду - виїзду</th>
                        <th scope="col">Кількість днів</th>
                        <th scope="col">Вільні місця</th>
                        <th scope="col">Ціна</th>
                        <th scope="col">Опис</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list changes as change>
                        <tr>
                            <th scope="row">${change.number}</th>
                            <td>${change.beginDate?date('yyyy-MM-dd')?string('dd/MM/yyyy')}${" - "}${change.endDate?date('yyyy-MM-dd')?string('dd/MM/yyyy')}</td>
                            <td>${change.duration()}</td>
                            <td>${change.freePlace}</td>
                            <td>${change.price}</td>
                            <td>
                                ${change.description}
                            </td>
                            <#if (isModerator && camp.author.id == currentID)>
                                <td><a href="/report/${change.id}">Звіт</a></td>
                            </#if>
                            <td><a href="/buy/${camp.id}/${change.id}">Купити</a></td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                </div>
            </div>
        </div>
        <br>
        <div class="modal-body"><div id="map"></div></div>
        <br>
        <div class="row">
            <div class="col">
                <p class="h4">Додати коментар, та оцінку:</p>
                <form action="/add/comment" method="post">
                    <div>
                        <label for="customRange1">Оцінка (0-10)</label>
                        <input type="range" class="custom-range" min="0" max="10" step="1" name="rate" />
                    </div>
                    <div class="form-group">
                        <label for="formGroupExampleInput">Додайте ваші враження від табору</label>
                        <input type="text" class="form-control" name="message" placeholder="Коментар..." />
                    </div>
                    <input type="hidden" name="campId" value="${camp.id}">
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <button class="btn btn-primary" type="submit">Додати коментар</button>
                </form>
            </div>
            <div class="col">
                <p class="h4">Коментарі:</p>

                <#if camp.comments?size==0>
                    <div class="alert alert-success" role="alert">
                        Ніхто поки не залишив коментарів, стань першим =)
                    </div>
                </#if>
                <#list camp.comments as comment>
                    <div class="card" style="width: 18rem; margin: 6px">
                        <div class="card-body">
                            <h5 class="card-title">${comment.author}</h5>
                            <h6 class="card-subtitle mb-2 text-muted">${comment.rate}</h6>
                            <p class="card-text">${comment.message}</p>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</@common.page>
