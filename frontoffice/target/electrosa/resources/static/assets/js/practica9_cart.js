console.log("El script practica9_cart.js se ha cargado correctamente");
document.addEventListener('DOMContentLoaded', function() {
    const quantityInputs = document.querySelectorAll('.quantity-input');
    
    quantityInputs.forEach(input => {
        input.dataset.oldValue = input.value;
        
        input.addEventListener('change', function() {
            const codigoArticulo = this.getAttribute('data-line-id');
            const newQuantity = parseInt(this.value);
            
            if (isNaN(newQuantity)) {
                showError(this, 'Por favor ingrese un número válido');
                return;
            }
            
            if (newQuantity < 1) {
                showError(this, 'La cantidad mínima es 1');
                return;
            }
            
            updateCartLine(codigoArticulo, newQuantity, this);
        });
    });
});

function updateCartLine(codigoArticulo, newQuantity, inputElement) {
  const card = inputElement.closest('.cesta-item-card');
  card.classList.add('updating');

  fetch(UPDATE_CART_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: `lineId=${encodeURIComponent(codigoArticulo)}&quantity=${encodeURIComponent(newQuantity)}`
  })
  .then(response => {
    if (!response.ok) throw new Error('Error en la respuesta del servidor');
    return response.json();
  })
  .then(data => {
    if (!data.success) throw new Error(data.message || 'Error al actualizar la cesta');

    updatePrice(
      card.querySelector('.precio-total span'),
      data.lineTotal
    );

    updatePrice(
      document.querySelector('.total-cesta span:last-child'),
      data.cartTotal
    );

    inputElement.value = data.newQuantity || newQuantity;
    inputElement.dataset.oldValue = inputElement.value;
  })
  .catch(error => {
    console.error('Error:', error);
    showError(inputElement, error.message);
  })
  .finally(() => {
    card.classList.remove('updating');
  });
}


function updatePrice(element, value) {
    if (element) {
        element.textContent = value.toFixed(2) + '€';
    }
}

function showError(inputElement, message) {
    alert(message);
    inputElement.value = inputElement.dataset.oldValue;
    inputElement.focus();
    inputElement.classList.add('error');
    setTimeout(() => inputElement.classList.remove('error'), 1000);
}