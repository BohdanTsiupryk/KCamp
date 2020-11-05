<#macro map lat long>
    <style type="text/css">
        #map {
            height: 600px;
            max-width: none;
        }
    </style>
    <script src="https://polyfill.io/v3/polyfill.min.js?features=default"></script>
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyDNH2SfyJ01U43bFcZdJ0TZ7LIk_QZjmzc&callback=initMap&libraries=&v=weekly" defer ></script>
    <script type="text/javascript" >
        function initMap() {
            const camp = new google.maps.LatLng(parseFloat(${long}), parseFloat(${lat}));
            const map = new google.maps.Map(document.getElementById("map"), {
                zoom: 12,
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