document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const title = urlParams.get('title') || 'Default Title';
    const text = urlParams.get('text') || 'Default Text';
    const email = urlParams.get('email') || 'Default Email';
    const telephone = urlParams.get('telephone') || 'Default Phone Number';
    const nickName = urlParams.get('nickName') || 'Default Nickname';
    const images = urlParams.get('images') || ''; // This will be a comma-separated list of image URLs

    // Set the text content for title, text, email, and telephone
    document.getElementById('announcement-title').textContent = title;
    document.getElementById('announcement-text').textContent = text;
    document.getElementById('announcement-email').textContent = email;
    document.getElementById('announcement-telephone').textContent = telephone;
    document.getElementById('announcement-nickname').textContent = nickName;

    // Handle images: Check if images exist
    const imageContainer = document.getElementById('announcement-images-container');
    if (images) {
        const imageUrls = images.split(',');  // Split the comma-separated list of image URLs
        imageContainer.innerHTML = ''; // Clear previous images

        if (imageUrls.length > 0 && imageUrls[0] !== '') {
            imageUrls.forEach((imgUrl, index) => {
                const imgElement = document.createElement('img');
                imgElement.src = imgUrl;
                imgElement.alt = `Announcement Image ${index + 1}`;
                imgElement.style.width = '100%';
                imgElement.style.maxWidth = '400px';
                imgElement.style.margin = '10px';
                imageContainer.appendChild(imgElement);
            });
        }
    }
});