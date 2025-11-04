function initializeWishlist() {
    if (!localStorage.getItem('wishlist')) {
        localStorage.setItem('wishlist', JSON.stringify([]));
    }
}

function getWishlist() {
    return JSON.parse(localStorage.getItem('wishlist')) || [];
}

function updateWishlistCount() {
    const wishlist = getWishlist();
    const count = wishlist.length;
    
    const desktopCount = document.getElementById('wishlist-count');
    if (desktopCount) {
        desktopCount.textContent = count;
        desktopCount.style.display = count > 0 ? 'inline-block' : 'none';
    }
    
    const mobileCounts = document.querySelectorAll('.mobile-bottom-navigation .action-btn:nth-child(4) .count');
    mobileCounts.forEach(el => {
        el.textContent = count;
        el.style.display = count > 0 ? 'inline-block' : 'none';
    });
}

function handleWishlistButtonClick(button) {
    const articleCode = button.getAttribute('data-article-code');
    if (!articleCode) return;
    
    let wishlist = getWishlist();
    const icon = button.querySelector('ion-icon');
    
    if (wishlist.includes(articleCode)) {
        wishlist = wishlist.filter(item => item !== articleCode);
        icon.name = 'heart-outline';
        button.classList.remove('active');
    } else {
        wishlist.push(articleCode);
        icon.name = 'heart';
        button.classList.add('active');
    }
    
    localStorage.setItem('wishlist', JSON.stringify(wishlist));
    updateWishlistCount();
}

function initWishlistButtons() {
    const buttons = document.querySelectorAll('.wishlist-btn');
    const wishlist = getWishlist();
    
    buttons.forEach(button => {
        const articleCode = button.getAttribute('data-article-code');
        if (wishlist.includes(articleCode)) {
            button.querySelector('ion-icon').name = 'heart';
            button.classList.add('active');
        }
        
        button.addEventListener('click', function(e) {
            e.preventDefault();
            handleWishlistButtonClick(button);
        });
    });
}

function setupWishlistLink() {
    const wishlistLink = document.getElementById('wishlist-link');
    if (wishlistLink) {
        wishlistLink.addEventListener('click', function(e) {
            const wishlist = getWishlist();
            if (wishlist.length === 0) {
                e.preventDefault();
                alert('No tienes artículos en tu lista de favoritos');
            } else {
                this.href = `/electrosa/cliente/wishlist?favoritos=${wishlist.join(',')}`;
            }
        });
    }
}

document.addEventListener('DOMContentLoaded', function() {
    initializeWishlist();
    updateWishlistCount();
    initWishlistButtons();
    setupWishlistLink();
});

window.wishlistUtils = {
    updateCount: updateWishlistCount,
    handleButtonClick: handleWishlistButtonClick
};