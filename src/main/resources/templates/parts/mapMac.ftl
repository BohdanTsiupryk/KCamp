<#macro map lat lng apiKey>
    <style type="text/css">
        #map {
            height: 600px;
            max-width: none;
        }
    </style>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap&libraries=&v=weekly" defer ></script>
    <script type="text/javascript" >
        function initMap() {
            const camp = new google.maps.LatLng(parseFloat(${lat}), parseFloat(${lng}));
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 14,
                center: camp
            });
            const marker = new google.maps.Marker({
                position: camp,
                map: map
            });
        }
        function resize() {
            initMap();
            console.log("Map ready");
            google.maps.event.trigger(document.getElementById("map"), "resize");
        }

    </script>
</#macro>

<#macro multyMap coords apiKey>
    <style type="text/css">
        #map {
            height: 600px;
            max-width: none;
        }
    </style>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=${apiKey}&callback=initMap&libraries=&v=weekly" defer ></script>
    <script src="https://unpkg.com/@googlemaps/markerclustererplus/dist/index.min.js"></script>
    <script type="text/javascript" >
        function initMap() {
            const labels = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 10,
                center: { lat : ${coords[0].latitude}, lng : ${coords[0].longitude} }
            });
            const markers = locations.map((location, i) => {
                return new google.maps.Marker({
                    position: location,
                    label: labels[i % labels.length],
                });
            });
            new MarkerClusterer(map, markers, {
                imagePath:
                    "https://unpkg.com/@googlemaps/markerclustererplus@1.0.3/images/m5.png",
            });
        }
        const locations = [
            <#list coords as coord>
            { lat : ${coord.latitude}, lng : ${coord.longitude} }<#sep>,
            </#list>
        ];

        function resize() {
            initMap();
            console.log("Map ready");
            google.maps.event.trigger(document.getElementById("map"), "resize");
        }

    </script>

</#macro>