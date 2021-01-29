<#macro report part>
    <div>
        <ul class="list-group list-group-flush">
            <dl class="row">
                <#list part.info as key, value>
                    <dt class="col-sm-3">${key}</dt>
                    <dd class="col-sm-9">${value}</dd>
                </#list>
                <dt class="col-sm-3">Громадянство</dt>
                <dd class="col-sm-9">
                    <#list part.citizenship as country, number>
                        <h6>${country}<span class="badge badge-secondary">${number}</span></h6>
                    </#list>
                </dd>
                <dt class="col-sm-3">Міста проживання</dt>
                <dd class="col-sm-9">
                    <#list part.userCity as city, number>
                        <h6>${city}<span class="badge badge-secondary">${number}</span></h6>

                    </#list>
                </dd>
                <dt class="col-sm-3">Вік дітей</dt>
                <dd class="col-sm-9">
                    <#list part.childAge as city, number>
                        <h6>${city}<span class="badge badge-secondary">${number}</span></h6>
                    </#list>
                </dd>
            </dl>
        </ul>
    </div>
</#macro>
<#macro diagram rep name>
        <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
        <script type="text/javascript">
            google.charts.load('current', {'packages':['corechart']});
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {

                var data = google.visualization.arrayToDataTable([
                    ['Місто', 'Кількість'],
                    <#list rep as city, percent>
                    ['${city}',${percent}]<#sep>,
                    </#list>
                ]);
                var options = {
                    title: '${name}' + ' statistic'
                };

                var chart = new google.visualization.PieChart(document.getElementById('${name}'));

                chart.draw(data, options);
            }
        </script>
</#macro>