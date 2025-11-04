(function() {
  'use strict';

  /**
   * Alterna el tipo de input entre 'password' y 'text'
   * @param {HTMLInputElement} inputEl  Campo de entrada de contraseña
   * @param {HTMLAnchorElement} toggleEl  Enlace que dispara el toggle
   */
  function setupPasswordToggle(inputEl, toggleEl) {
    if (!inputEl || !toggleEl) return;

    toggleEl.addEventListener('click', function(event) {
      event.preventDefault();
      const isPassword = inputEl.getAttribute('type') === 'password';
      inputEl.setAttribute('type', isPassword ? 'text' : 'password');

      const span = toggleEl.querySelector('span');
      if (span) {
        span.textContent = isPassword ? 'Ocultar contraseña' : 'Mostrar contraseña';
      }
      const icon = toggleEl.querySelector('ion-icon');
      if (icon) {
        icon.setAttribute('name', isPassword ? 'eye-off' : 'eye');
      }
    });
  }

  function init() {
    const toggles = [
      { inputId: 'userpwd1', toggleId: 'pwd1-toggle' },
      { inputId: 'userpwd2', toggleId: 'pwd2-toggle' }
    ];

    toggles.forEach(({ inputId, toggleId }) => {
      const inputEl = document.getElementById(inputId);
      const toggleEl = document.getElementById(toggleId);
      setupPasswordToggle(inputEl, toggleEl);
    });
  }

  document.addEventListener('DOMContentLoaded', init);
})();
