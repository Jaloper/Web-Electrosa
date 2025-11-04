document.addEventListener('DOMContentLoaded', function() {
    const toast = document.getElementById('notification-toast');
    const toastTitle = document.getElementById('toast-title');
    const toastCloseBtn = document.querySelector('[data-toast-close]');
    const apiUrl = toast.getAttribute('data-url');
    
    let updateInterval;
    
    function fetchLastPurchase() {
        fetch(apiUrl)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(articulo => {
                if (articulo && articulo.nombre) {
                    toastTitle.textContent = articulo.nombre;
                    
                    toast.style.animationPlayState = 'running';
                    
                    toast.classList.remove('closed');
                }
            })
            .catch(error => {
                console.error('Error al obtener el último artículo:', error);
            });
    }
    
    function startUpdates() {
        fetchLastPurchase();
        
        updateInterval = setInterval(fetchLastPurchase, 10000);
    }

    toastCloseBtn.addEventListener('click', function() {
        toast.classList.add('closed');
    });
    
    startUpdates();
    
    window.addEventListener('beforeunload', function() {
        if (updateInterval) {
            clearInterval(updateInterval);
        }
    });
});

