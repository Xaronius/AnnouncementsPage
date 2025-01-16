document.addEventListener('DOMContentLoaded', function () {
    const urlParams = new URLSearchParams(window.location.search);
    const adId = urlParams.get('adId');  // Get the adId from the URL

    if (adId) {
        fetch(`/api/announcements/${adId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch announcement details');
                }
                return response.json();
            })
            .then(announcement => {
                // Populate the page with announcement data
                document.getElementById('announcement-title').textContent = announcement.title;
                document.getElementById('announcement-text').textContent = announcement.description;
                document.getElementById('announcement-email').textContent = `Email: ${announcement.contactEmail}`;
                document.getElementById('announcement-telephone').textContent = `Phone: ${announcement.contactPhone}`;
                document.getElementById('announcement-nickname').textContent = `User ID: ${announcement.nickName}`;

                const imageContainer = document.getElementById('announcement-images-container');
                imageContainer.innerHTML = '';  // Clear existing images

                if (announcement.images && announcement.images.length > 0) {
                    announcement.images.forEach((base64Image, index) => {
                        const imgElement = document.createElement('img');
                        imgElement.src = 'data:image/jpeg;base64,' + base64Image;
                        imgElement.alt = `Announcement Image ${index + 1}`;
                        imgElement.style.width = '100%';
                        imgElement.style.maxWidth = '400px';
                        imgElement.style.margin = '10px';
                        imageContainer.appendChild(imgElement);
                    });
                }
            })
            .catch(error => console.error('Error fetching announcement:', error));
    } else {
        console.error('No adId provided in URL');
    }
});