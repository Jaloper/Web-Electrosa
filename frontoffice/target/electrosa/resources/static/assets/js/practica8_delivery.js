(function() {
  'use strict';
  function transformData(pedidoStats) {
    const regiones = ['PB', 'IB', 'IN'];
    const monthNames = ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun', 'Jul', 'Ago', 'Sep', 'Oct', 'Nov', 'Dic'];

    const datos = {
      PB: Array(12).fill(null).map(() => []),
      IB: Array(12).fill(null).map(() => []),
      IN: Array(12).fill(null).map(() => []),
    };

    pedidoStats.forEach(pedido => {
      const mesIdx = pedido.mesEnvio - 1;
      const region = pedido.regionEnvio;

      if (
        typeof pedido.difFecha === 'number' &&
        datos.hasOwnProperty(region) &&
        mesIdx >= 0 && mesIdx < 12
      ) {
        datos[region][mesIdx].push(pedido.difFecha);
      } else {
        console.warn('Pedido inválido detectado:', pedido);
      }
    });

    // Calcula el promedio de un array de números
    function promedio(arr) {
      if (!arr.length) return 0;
      return arr.reduce((sum, n) => sum + n, 0) / arr.length;
    }

    // Construir series para cada región
    const series = regiones.map(region => ({
      name: region,
      data: datos[region].map(promedio)
    }));

    return {
      categories: monthNames,
      series: series
    };
  }

  function initChart() {
    const stats = window.pedidoStats;
    if (!Array.isArray(stats) || stats.length === 0) {
      console.error('No hay datos de pedidos para mostrar el gráfico.');
      return;
    }

    console.log('Datos de pedidos para gráfico:', stats);

    const chartData = transformData(stats);

    toastui.Chart.lineChart({
      el: document.getElementById('chart'),
      data: chartData,
      options: {
        chart: { title: 'Tiempo Medio de Entrega por Región' },
        xAxis: { title: 'Mes', type: 'category' },
        yAxis: { title: 'Días de Entrega' },
        series: { spline: true },
        legend: { visible: true }
      }
    });
  }

  document.addEventListener('DOMContentLoaded', initChart);
})();
