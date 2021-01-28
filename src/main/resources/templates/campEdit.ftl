<#import "parts/common.ftl" as common>

<@common.page>
    <p class="h3">Редагування інформації про табір <i>${camp.nameCamp}</i></p>
    <div>
        <form action="/camp/update" method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <input type="hidden" name="id" value="${camp.id}"/>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Назва табору</span>
                </div>
                <input type="text" class="form-control" name="campName" value="${camp.nameCamp}" />
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Адресса</span>
                </div>
                <input type="text" class="form-control" name="address" value="${camp.address}" />
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Опис</span>
                </div>
                <textarea class="form-control" name="description"  name="description">${camp.description}</textarea>
            </div>
            <div class="input-group input-group-sm mb-3">
                <img src="${camp.mainPicName}" alt="Main Picture" class="rounded float-left" height="200" />

            </div>
            <div class="input-group input-group-sm mb-3">
                <#list camp.campPhotos as photo>
                    <img class="rounded float-left" src="${photo}" height="200" class="d-block w-200" alt="${photo}" />
                </#list>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Вибрати головне нове фото</span>
                </div>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" name="image" >
                    <label class="custom-file-label" for="inputGroupFile01">Оберіть файл...</label>
                </div>
            </div>
            <div class="input-group mb-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">Додати нові фото</span>
                </div>
                <div class="custom-file">
                    <input type="file" class="custom-file-input" accept="image/*" multiple name="photos" >
                    <label class="custom-file-label" for="inputGroupFile01">Оберіть файл...</label>
                </div>
            </div>
            <div>
            <button type="submit" class="btn btn-primary">Зберегти</button>
            </div>
        </form>
    </div>
    <p>
        <a class="btn btn-primary" data-toggle="collapse" href="#changeAdd" role="button">
            Додати зміну
        </a>
    </p>
    <div class="collapse"  id="changeAdd">
        <form action="/camp/change/add" method="post" enctype="multipart/form-data">
            <div class="container">
                <div class="row">
                    <div class="col-sm">
                        <div class="form-group">
                            <label>Номер</label><br>
                            <input type="number" class="form-control" name="number" placeholder="Номер зміни" required /><br>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label>Дата початку</label><br>
                            <input type="date" class="form-control" name="begin" required />
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label>Дата закінчення</label><br>
                            <input type="date" class="form-control" name="end" required />
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label>Ціна</label><br>
                            <input type="number" class="form-control" name="price" placeholder="Ціна" required /><br>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label>Кількість місць</label><br>
                            <input type="number" class="form-control" name="places" placeholder="Кількість місць" required /><br>
                        </div>
                    </div>
                    <div class="col-sm">
                        <div class="form-group">
                            <label>Короткий опис</label><br>
                            <input type="text" class="form-control" name="description" placeholder="Короткий опис" required /><br>
                        </div>
                    </div>
                    <div class="col-sm">
                        <label>&nbsp;</label><br>
                        <button type="submit" class="btn btn-primary">Додати</button>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <input type="hidden" name="campId" value="${camp.id}"/>
            </div>
        </form>
    </div>

    <div>
        <#list camp.getChanges() as change>
            <form method="post" action="/camp/change/update">
                <div class="container">
                    <div class="row">
                        <div class="col-sm">
                            <div class="form-group">
                                <label>Дата початку</label><br>
                                <input type="date" class="form-control"  name="beginDate" value="${change.beginDate}" />
                            </div>
                        </div>
                        <div class="col-sm">
                            <div class="form-group">
                                <label>Дата закінчення</label><br>
                                <input type="date" class="form-control" name="endDate" value="${change.endDate}" />
                            </div>
                        </div>
                        <div class="col-sm">
                            <div class="form-group">
                                <label>Ціна</label><br>
                                <input type="number" class="form-control" name="price" value="${change.price}" placeholder="Ціна" />
                            </div>
                        </div>
                        <div class="col-sm">
                            <div class="form-group">
                                <label>Кількість місць</label><br>
                                <input type="number" class="form-control" name="places" value="${change.places}" placeholder="Кількість місць" />
                            </div>
                        </div>
                        <div class="col-sm">
                            <div class="form-group">
                                <label>Короткий опис</label><br>
                                <input type="text" class="form-control" name="description" placeholder="Короткий опис" value="${change.description}" />
                            </div>
                        </div>
                        <div class="col-sm">
                            <label>&nbsp;</label><br>
                            <button type="submit" class="btn btn-primary">Зберегти</button>
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <input type="hidden" name="changeId" value="${change.id}"/>
                    <input type="hidden" name="campId" value="${camp.id}"/>
                </div>
            </form>
        </#list>
    </div>
</@common.page>