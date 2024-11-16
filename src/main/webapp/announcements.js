// Extract title and text from the query parameters in the URL
const urlParams = new URLSearchParams(window.location.search);
const title = urlParams.get('title');
const text = urlParams.get('text');

// Inject them into the HTML
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('announcement-title').textContent = title || 'Default Title';
    document.getElementById('announcement-text').textContent = text || 'Default Text';
});