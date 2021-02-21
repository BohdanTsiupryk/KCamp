<#import "parts/common.ftl" as common>

<@common.page>
    <div class="input-group shadow-sm p-3 mb-5 bg-white rounded">
        <div class="input-group-prepend" id="button-addon3">
            <button class="btn btn-outline-secondary" type="button" data-toggle="collapse" data-target="#filter"
                    aria-expanded="false" aria-controls="collapseExample">
                Фільтри
            </button>
            <button class="btn btn-outline-secondary" type="button" data-toggle="collapse" data-target="#around"
                    aria-expanded="false" aria-controls="collapseExample">
                Пошук поряд
            </button>
            <a class="btn btn-outline-secondary" href="/sort" role="button">
                Відсортувати по рейтингу
            </a>
            <button class="btn btn-outline-danger" type="button" data-toggle="collapse" data-target="#anketa"
                    aria-expanded="false" aria-controls="collapseExample">
                Не вдається знайти найкращий табір?
            </button>
        </div>
    </div>
    <div class="collapse" id="anketa">
        <form action="/search/questionnaire" method="post" name="questionnaire">
            <h3>Дайте відповідь на декілька простих запитань, і наша система підбере для вас ідеальні табори</h3>
            <ul class="list-group">
                <li class="list-group-item w-50 p-3"><label>1. Вкажіть місце вашого проживання<input type="text"
                                                                                                     name="address"
                                                                                                     placeholder="Місто вашого проживання..."
                                                                                                     style="width: 300px"
                                                                                                     class="form-control"/></label>
                </li>
                <li class="list-group-item w-50 p-3"><label>2. Наскільки далеко може знаходитися табір?<input
                                                                                                    type="number"
                                                                                                    name="maxDistance"
                                                                                                    placeholder="Радіус пошуку у кілометрах..."
                                                                                                    style="width: 300px"
                                                                                                    class="form-control"/></label>
                </li>
                <li class="list-group-item w-50 p-3"><label>3. Ключові клова при пошуку<textarea name="keyWords"
                                                                                                 placeholder="Ключові слова"
                                                                                                 style="width: 300px"
                                                                                                 class="form-control"></textarea>
                    </label>
                </li>
                <li class="list-group-item w-50 p-3"><label>4. Табірні особливості (Можна обрати декілька)
                        <select multiple="multiple" class="form-control" style="width: 300px" name="interests">
                            <#list interests as inter>
                                <option value="${inter}">${inter.getDescription()}</option>
                            </#list>
                        </select>
                    </label>
                </li>
                <li class="list-group-item w-50 p-3"><label>5. Розташування (Можна обрати декілька)
                        <select multiple="multiple" class="form-control" style="width: 300px" name="locations">
                            <#list locations as location>
                                <option value="${location}">${location.getDescription()}</option>
                            </#list>
                        </select>
                    </label>
                </li>
                <li class="list-group-item w-50 p-3"><label>6. Вікова категорія (Можна обрати декілька)
                        <select multiple="multiple" class="form-control" style="width: 300px" name="childhoods">
                            <#list childhoods as hood>
                                <option value="${hood}">${hood.getDescription()}</option>
                            </#list>
                        </select>
                    </label>
                </li>
            </ul>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <br>
            <button class="btn btn-success btn-lg btn-block" type="submit">Підібрати</button>
        </form>
        <br>
    </div>
    <div class="collapse" id="around">
        <form action="/search/location" method="post" enctype="multipart/form-data" accept-charset="UTF-8">
            <div class="row">
                <div class="col"><input type="text" name="address" placeholder="Місто вашого проживання..."
                                        class="form-control"/></div>
                <div class="col"><input type="text" name="maxDistance" placeholder="Радіус пошуку у кілометрах..."
                                        class="form-control"/></div>
                <div class="col"><input type="hidden" name="_csrf" value="${_csrf.token}"/></div>
                <div class="col"><input type="submit" value="Шукати поряд" class="btn btn-primary"/></div>
            </div>
        </form>
        <br>
    </div>
    <div class="collapse" id="filter">
        <form action="/filter" method="post" name="filter" enctype="multipart/form-data">
            <div class="form-row">
                <div class="col">
                    <label>Табірні особливості (Можна обрати декілька)</label>
                    <select multiple="multiple" class="form-control" name="interests">
                        <#list interests as inter>
                            <option value="${inter}">${inter.getDescription()}</option>
                        </#list>
                    </select>
                </div>
                <div class="col">
                    <label>Розташування (Можна обрати декілька)</label>
                    <select multiple="multiple" class="form-control" name="locations">
                        <#list locations as location>
                            <option value="${location}">${location.getDescription()}</option>
                        </#list>
                    </select>
                </div>
                <div class="col">
                    <label>Вікова категорія (Можна обрати декілька)</label>
                    <select multiple="multiple" class="form-control" name="childhoods">
                        <#list childhoods as hood>
                            <option value="${hood}">${hood.getDescription()}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <br>
            <button class="btn btn-primary" type="submit">Фільтрувати</button>
        </form>
        <br>
    </div>
    <div class="card-columns">
        <#list camps as camp>
            <div class="card my-3" style="width: 18rem;">
                <div class="card-header">
                    <a href="/camp/profile/${camp.id}">${camp.nameCamp}</a>
                    <#if camp.rating != 0>Рейтинг ${camp.rating}/10 <#else> Ніхто не оцінив табір </#if>
                </div>
                <#if camp.mainPicName??>
                    <img class="card-img-top" src="${camp.mainPicName}" height="200">
                </#if>
                <div class="m-2">
                    <span class="card-text">
                        <#if (camp.description?length < 150)>
                            ${camp.description}
                        <#else>
                            ${camp.description?substring(0, 140)}<a href="/camp/profile/${camp.id}">...</a>
                        </#if>
                    </span>
                </div>
                <div class="card-footer text-muted">
                    ${camp.author.username}
                </div>
            </div>
        </#list>
    </div>
</@common.page>
