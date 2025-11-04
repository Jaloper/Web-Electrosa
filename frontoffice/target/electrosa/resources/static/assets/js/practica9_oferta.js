document.addEventListener('DOMContentLoaded', function () {
    const descubrirOfertaBtn = document.getElementById('descubrir-oferta');
    const articleId = window.location.pathname.split('/').pop();

    fetch(`/electrosa/api/articulo/${articleId}/oferta`)
            .then(response => {
                if (!response.ok)
                    return null;
                return response.json();
            })
            .then(data => {
                if (!data || data.activa !== 1) {
                    return;
                }
                descubrirOfertaBtn.style.display = 'inline-block';
            })
            .catch(() => {
            });

    if (descubrirOfertaBtn) {
        descubrirOfertaBtn.addEventListener('click', function (e) {
            e.preventDefault();
            e.stopPropagation();
            descubrirOfertaBtn.style.display = 'none';

            const ofertaContainer = document.getElementById('oferta-container') || crearContenedorOferta();

            let intervaloActivo = true;
            let precioModificado = false;

            //Fecha fin de la oferta= Fecha actual + 3 meses
            const fechaActual = new Date();
            const fechaFinOferta = new Date(fechaActual);
            fechaFinOferta.setMonth(fechaFinOferta.getMonth() + 3);

            const fetchData = () => {
                if (!intervaloActivo)
                    return;

                fetch(`/electrosa/api/articulo/${articleId}/oferta`)
                        .then(response => {
                            if (!response.ok) {
                                intervaloActivo = false;
                                return null;
                            }
                            return response.json();
                        })
                        .then(data => {
                            if (!data || data.activa !== 1) {
                                intervaloActivo = false;
                                return;
                            }

                            if (!precioModificado) {
                                const precioOriginalElement = document.querySelector('.ficha-info .precio');
                                if (precioOriginalElement) {
                                    const precioOriginal = parseFloat(precioOriginalElement.textContent.replace(' €', '').replace(',', '.'));
                                    const precioConDescuento = precioOriginal * (1 - data.descuento / 100);

                                    if (!precioOriginalElement.dataset.originalHtml) {
                                        precioOriginalElement.dataset.originalHtml = precioOriginalElement.innerHTML;
                                    }

                                    const N = Math.round(100 / data.descuento);
                                    const M = N - 1;

                                    precioOriginalElement.innerHTML = `
    <div>
        <div>
            <span style="text-decoration: line-through; color: #777;">${precioOriginal.toFixed(2)} €</span>
            <span style="color: #f76e7e; font-weight: bold; margin-left: 10px;">${precioConDescuento.toFixed(2)} €</span>
        </div>
        <div style="font-size: 0.9em; color: #333; margin-top: 5px;">
            Al comprar ${N} pagas ${M}
        </div>
    </div>
`;


                                    precioModificado = true;
                                }
                            }

                            const ahora = new Date();
                            const segundosRestantes = Math.max(0, Math.floor((fechaFinOferta - ahora) / 1000));

                            mostrarOfertaCompleta({
                                descuento: data.descuento,
                                segundosRestantes: segundosRestantes,
                                fechaFin: fechaFinOferta.toDateString()
                            }, ofertaContainer);

                            if (segundosRestantes <= 0) {
                                intervaloActivo = false;
                            }
                        })
                        .catch(() => {
                            intervaloActivo = false;
                        });
            };

            fetchData();
            const intervalId = setInterval(fetchData, 1000);
            setTimeout(() => clearInterval(intervalId), 3600000);
        });

    }
});


function crearContenedorOferta() {
    const btn = document.getElementById('descubrir-oferta');
    const container = document.createElement('div');
    container.id = 'oferta-container';
    container.style.cssText = `
        position: fixed;
        right: 20px;
        top: 50%;
        transform: translateY(-50%);
        background: white;
        padding: 20px;
        border-radius: 10px;
        box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        z-index: 1000;
        width: 300px;
    `;
    btn.insertAdjacentElement('afterend', container);
    return container;
}

function mostrarOfertaCompleta(datos, container) {
    const tiempo = convertirSegundosATiempo(datos.segundosRestantes);

    let fechaFinMostrar = '';
    if (datos.fechaFin) {
        const partes = datos.fechaFin.split(' ');
        if (partes.length === 3) {
            const año = parseInt(partes[2]) + 1;
            fechaFinMostrar = `Válido hasta ${partes[0]} ${partes[1]} ${año}`;
        }
    }

    const tiempoTexto = datos.segundosRestantes > 0
            ? `
            <div style="margin-top: 15px;">
                <p style="margin-bottom: 8px; font-weight: bold;">Tiempo restante:</p>
                <div style="display: flex; gap: 8px;">
                    <div class="time-box">
                        <div class="time-value">${tiempo.dias}</div>
                        <div class="time-label">días</div>
                    </div>
                    <div class="time-box">
                        <div class="time-value">${tiempo.horas}</div>
                        <div class="time-label">horas</div>
                    </div>
                    <div class="time-box">
                        <div class="time-value">${tiempo.minutos}</div>
                        <div class="time-label">min</div>
                    </div>
                    <div class="time-box">
                        <div class="time-value">${tiempo.segundos}</div>
                        <div class="time-label">seg</div>
                    </div>
                </div>
            </div>
        `
            : '<p style="color: #ff4444;">Oferta finalizada</p>';

    container.innerHTML = `
        <div class="oferta-info">
            ${tiempoTexto}
        </div>
    `;

    const style = document.createElement('style');
    style.textContent = `
        .time-box {
            background: #f5f5f5;
            border-radius: 8px;
            padding: 8px;
            text-align: center;
            min-width: 50px;
        }
        .time-value {
            font-size: 1.4em;
            font-weight: bold;
            color: #333;
        }
        .time-label {
            font-size: 0.8em;
            color: #666;
            margin-top: 4px;
        }
    `;
    document.head.appendChild(style);
}

function convertirSegundosATiempo(segundos) {
    const dias = Math.floor(segundos / (3600 * 24));
    segundos %= 3600 * 24;
    const horas = Math.floor(segundos / 3600);
    segundos %= 3600;
    const minutos = Math.floor(segundos / 60);
    segundos %= 60;

    return {dias, horas, minutos, segundos};
}