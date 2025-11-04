document.addEventListener('DOMContentLoaded', function () {
    delete L.Icon.Default.prototype._getIconUrl;
    L.Icon.Default.mergeOptions({
        iconRetinaUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon-2x.png',
        iconUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-icon.png',
        shadowUrl: 'https://unpkg.com/leaflet@1.9.4/dist/images/marker-shadow.png'
    });

    const map = L.map('mapId', {
        center: [40.4169473, -3.7035285],
        zoom: 6
    });

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    almacenes.forEach(almacen => {
        L.marker([almacen.coordX, almacen.coordY])
            .bindPopup(`${almacen.calle}, ${almacen.ciudad}`)
            .addTo(map);
    });
     let userCircle = null;
    document.getElementById('btnUbicacion').addEventListener('click', function () {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                mostrarPosicion,
                manejarError,
                {
                    enableHighAccuracy: true,
                    timeout: 10000,
                    maximumAge: 0
                }
            );
        } else {
            alert("Tu navegador no soporta geolocalización.");
        }
    });

    function mostrarPosicion(pos) {
        const lat = pos.coords.latitude;
        const lon = pos.coords.longitude;

        if (userCircle) {
            map.removeLayer(userCircle);
        }

        userCircle = L.circle([lat, lon], {
            color: '#5C7E74',
            fillColor: '#5C7E74',
            fillOpacity: 0.5,
            radius: 20000
        }).addTo(map);

    }

    function manejarError(error) {
        alert("Error al obtener ubicación: " + error.message);
    }
});
