document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    console.log('All Parameters:', Array.from(urlParams.entries())); // Debug log

    const title = urlParams.get('title') || 'Default Title';
    const text = urlParams.get('text') || 'Default Text';
    const email = urlParams.get('email') || 'Default Email';
    const telephone = urlParams.get('telephone') || 'Default Phone Number';
    const nickName = urlParams.get('nickName') || 'Default Nickname';
    const image = urlParams.get('image') || '';


    // Set the text content for title, text, email, and telephone
    document.getElementById('announcement-title').textContent = title;
    document.getElementById('announcement-text').textContent = text;
    document.getElementById('announcement-email').textContent = email;
    document.getElementById('announcement-telephone').textContent = telephone;
    document.getElementById('announcement-nickname').textContent = nickName;

    // Display the image if the URL is available
    if (image) {
        const imageElement = document.getElementById('announcement-image');
        imageElement.src = image; // Set the image source to the URL from the query parameters
    }
});
