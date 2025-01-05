// Extract title and text from the query parameters in the URL
const urlParams = new URLSearchParams(window.location.search);
const title = urlParams.get('title');
const text = urlParams.get('text');
const email = urlParams.get('email');
const telephone = urlParams.get('telephone');
const image = urlParams.get('image');

// Inject them into the HTML
document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('announcement-title').textContent = title || 'Default Title';
    document.getElementById('announcement-text').textContent = text || 'Default Text';
    document.getElementById('announcement-email').textContent = email || 'Default Email';
    document.getElementById('announcement-telephone').textContent = telephone || 'Default Phone Number';
    if (image) {
        document.getElementById('announcement-image').src = image;
        document.getElementById('announcement-image').style.display = 'block';  // Make sure the image is visible
    } else {
        document.getElementById('announcement-image').style.display = 'none';  // Hide the image element if no image URL is provided
    }
});
