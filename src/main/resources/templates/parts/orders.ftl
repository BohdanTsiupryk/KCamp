<#macro list orders>
    <div>
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Табір</th>
                <th scope="col">Зміна №</th>
                <th scope="col">ПБІ дитини</th>
                <th scope="col">Дата заїзду</th>
                <th scope="col">Кількість днів</th>
                <th scope="col">Документ</th>
                <th scope="col">Ціна</th>
                <th scope="col">Опис</th>
            </tr>
            </thead>
            <tbody>
            <#list orders as order>
                <tr>
                    <th scope="row">${order.change.parentCamp.nameCamp}</th>
                    <th scope="row">${order.change.number}</th>
                    <#list order.child as child>
                        <td>${child.fullName}</td>
                    </#list>
                    <td>${order.change.beginDate}-${order.change.endDate}</td>
                    <td>${order.change.duration()}</td>
                    <#list order.child as child>
                        <td>${child.document}</td>
                    </#list>
                    <td>${order.change.price}</td>
                    <td>${order.change.description}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#macro>