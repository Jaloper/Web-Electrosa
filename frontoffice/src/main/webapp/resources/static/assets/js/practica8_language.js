window.addEventListener("load", init);
// o window.addEventListener("DOMContentLoaded", init);

function init() {
  let languageSelector = document.getElementById('language-selector');
  languageSelector.addEventListener('change', function(e) {
    this.form.submit();
  });
}