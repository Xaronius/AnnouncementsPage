document.addEventListener('DOMContentLoaded', function () {
    // Fetch the first 5 announcements from the backend API
    fetch('/api/announcements/top5')
        .then(response => {
            if (!response.ok) {
                throw new Error('No announcements found');
            }
            return response.json();
        })
        .then(data => {

            // Select all 5 announcement forms
            const forms = [
                document.querySelector('.mainPageAnnouncement1Div'),
                document.querySelector('.mainPageAnnouncement2Div'),
                document.querySelectorAll('.mainPageAnnouncement3Div')[0],
                document.querySelectorAll('.mainPageAnnouncement3Div')[1],
                document.querySelectorAll('.mainPageAnnouncement3Div')[2]
            ];

            // Populate each form with the fetched data
            forms.forEach((form, index) => {
                if (form && data[index]) {
                    const announcement = data[index];

                    const titleElement = form.querySelector('.MainPageAnnouncementHeader');
                    const textElement = form.querySelector('.MainPageAnnouncementText');
                    const emailElement = form.querySelector('input[name="email"]');
                    const telephoneElement = form.querySelector('input[name="telephone"]');
                    const nickNameElement = form.querySelector('input[name="nickName"]');
                    const imageInput = form.querySelector('input[name="image"]');

                    console.log("nickName Input:" + nickNameElement);
                    console.log("Image Input:" + imageInput);

                    // Set text content
                    if (titleElement) titleElement.textContent = announcement.title || 'No Title';
                    if (textElement) textElement.textContent = announcement.description || 'No Description';
                    if (emailElement) emailElement.value = announcement.contactEmail || 'No Email';
                    if (telephoneElement) telephoneElement.value = announcement.contactPhone || 'No Phone';
                    if (nickNameElement) nickNameElement.value = announcement.userId ? `User ${announcement.userId}` : 'Unknown User';

                    // Handle images properly
                    const images = announcement.images && announcement.images.length > 0
                        ? announcement.images.map(imgObj => imgObj.image)
                        : [];

                    // If images exist, display them, otherwise don't send anything
                    if (images.length > 0) {
                        // Create a container for images if it doesn't exist
                        let imageContainer = form.querySelector('.image-container');
                        if (!imageContainer) {
                            imageContainer = document.createElement('div');
                            imageContainer.classList.add('image-container');
                            imageContainer.style.display = 'flex';
                            imageContainer.style.gap = '10px';
                            form.insertBefore(imageContainer, form.querySelector('.AnnouncementButton'));
                        }

                        // Display images
                        images.forEach((imgUrl, imgIndex) => {
                            // Avoid adding the same hidden input again
                            if (!form.querySelector(`input[name="image${imgIndex + 1}"]`)) {
                                const img = document.createElement('img');
                                img.src = imgUrl;
                                img.style.width = '100px';
                                img.style.height = '100px';
                                img.style.objectFit = 'cover';
                                img.alt = `Announcement Image ${imgIndex + 1}`;
                                imageContainer.appendChild(img);

                                // Create hidden inputs only if they don't exist
                                const hiddenImageInput = document.createElement('input');
                                hiddenImageInput.type = 'hidden';
                                hiddenImageInput.name = `image${imgIndex + 1}`;
                                hiddenImageInput.value = imgUrl;
                                form.appendChild(hiddenImageInput);
                            }
                        });
                    }
                }
            });

            // After forms are populated, handle form submissions
            forms.forEach(function (form) {
                form.addEventListener('submit', function (event) {
                    event.preventDefault();

                    // Collect data from the form
                    const titleElement = form.querySelector('.MainPageAnnouncementHeader');
                    const textElement = form.querySelector('.MainPageAnnouncementText');
                    const emailElement = form.querySelector('input[name="email"]');
                    const telephoneElement = form.querySelector('input[name="telephone"]');
                    const nickNameElement = form.querySelector('input[name="nickName"]');

                    const title = titleElement ? encodeURIComponent(titleElement.textContent.trim()) : '';
                    const text = textElement ? encodeURIComponent(textElement.textContent.trim()) : '';
                    const email = emailElement ? encodeURIComponent(emailElement.value) : '';
                    const telephone = telephoneElement ? encodeURIComponent(telephoneElement.value) : '';
                    const nickName = nickNameElement ? encodeURIComponent(nickNameElement.value) : '';

                    // Collect all image URLs from hidden inputs (only if images exist)
                    const imageInputs = form.querySelectorAll('input[name^="image"]');
                    const images = Array.from(imageInputs)
                        .map(input => input.value)
                        .filter(url => url.trim() !== '') // Remove empty values
                        .map(url => encodeURIComponent(url)); // Then encode

                    // Debug log to check images array before adding to the URL
                    console.log("Collected Images:", images);

                    // Only append images query parameter if there are images (do not append if no images)
                    const imagesParam = images.length > 0 ? `&images=${images.join(',')}` : '';

                    // Debug log to check the URL
                    const redirectUrl = `/announcementPage.html?title=${title}&text=${text}&email=${email}&telephone=${telephone}&nickName=${nickName}${imagesParam}`;
                    console.log('Redirecting to:', redirectUrl);

                    // Redirect to the announcement page with the query parameters
                    window.location.href = redirectUrl;
                });
            });
        })
        .catch(error => {
            console.error('Error fetching announcements:', error);
        });
});

// ----------------------------- Dropdown ----------------------------- //
function myFunction() {
    document.getElementById("myDropdown").classList.toggle("show");
}

// Close the dropdown menu if the user clicks outside of it
window.onclick = function(event) {
    if (!event.target.matches('.dropButton')) {
        var dropdowns = document.getElementsByClassName("dropdown-content");
        var i;
        for (i = 0; i < dropdowns.length; i++) {
            var openDropdown = dropdowns[i];
            if (openDropdown.classList.contains('show')) {
                openDropdown.classList.remove('show');
            }
        }
    }
}
