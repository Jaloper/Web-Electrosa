document.addEventListener('DOMContentLoaded', function() {
    const baseUrl = window.location.origin + window.location.pathname.split('/').slice(0, 2).join('/');
    const bannerUrl = baseUrl + '/api/banner/header';
    
    fetch(bannerUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(text => {
            renderBanner(text);
        })
        .catch(error => {
            console.error('Error fetching banner:', error);
            renderBanner('<b>Promociones especiales</b> este mes para nuestros clientes');
        });

    function renderBanner(content) {
        const banner = document.getElementById('news-banner');
        if (banner) {
            banner.innerHTML = content;
            banner.style.display = 'block';
        }
    }
});