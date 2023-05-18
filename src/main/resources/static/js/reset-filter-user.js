const filterForm = document.getElementById('filterForm');
const resetFilterButton = filterForm.querySelector('button[type="reset"]');

resetFilterButton.addEventListener('click', () => {
  window.location.href = '/home#petCatalog';
});