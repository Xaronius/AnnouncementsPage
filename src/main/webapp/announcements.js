document.addEventListener('DOMContentLoaded', function () {
    let currentPage = 0; // Start with the first page
    const pageSize = 10; // Number of announcements per page
    let totalAnnouncements = 0; // To hold total announcements count
    let totalPages = 0; // To hold total pages count

    function fetchAnnouncements(page) {
        console.log(`Fetching announcements for page ${page}...`); // Log current page

        fetch(`/api/announcements/list?page=${page}&size=${pageSize}`)
            .then(response => response.json())
            .then(data => {
                console.log('Fetched data:', data);  // Log the full response for debugging

                // Extracting the necessary values from the response
                const announcements = data.content || []; // Ensure 'content' holds the actual records
                totalAnnouncements = data.totalElements || 0; // Total number of announcements
                totalPages = data.totalPages || 1; // Total number of pages

                console.log(`Total announcements: ${totalAnnouncements}, Total pages: ${totalPages}`);

                displayAnnouncements(announcements);
                updatePaginationVisibility(currentPage, totalPages); // Update pagination visibility based on current page
            })
            .catch(error => console.error('Error fetching announcements:', error));
    }

    function displayAnnouncements(announcements) {
        console.log('Received announcements:', announcements);  // Log the announcements to debug
        const container = document.querySelector('#announcements-container');  // Correct selector
        container.innerHTML = ''; // Clear previous announcements

        announcements.forEach(announcement => {
            const announcementForm = document.createElement('form');
            announcementForm.classList.add('AnnouncementsDiv'); // Match the main page form structure

            // Hidden input fields for the form submission
            const emailInput = document.createElement('input');
            emailInput.type = 'hidden';
            emailInput.name = 'email';
            emailInput.value = announcement.contactEmail;

            const telephoneInput = document.createElement('input');
            telephoneInput.type = 'hidden';
            telephoneInput.name = 'telephone';
            telephoneInput.value = announcement.contactPhone;

            const nickNameInput = document.createElement('input');
            nickNameInput.type = 'hidden';
            nickNameInput.name = 'nickName';
            nickNameInput.value = announcement.nickName || 'N/A';

            const imageInput = document.createElement('input');
            imageInput.type = 'hidden';
            imageInput.name = 'image';
            imageInput.value = announcement.images[0]?.image || ''; // Use the first image or leave empty

            // Create the submit button for the form
            const submitButton = document.createElement('button');
            submitButton.type = 'submit';
            submitButton.classList.add('AnnouncementButton');

            // Create the title and description elements
            const titleElement = document.createElement('h2');
            titleElement.classList.add('AnnouncementsTitle');
            titleElement.textContent = announcement.title;

            const descriptionElement = document.createElement('p');
            descriptionElement.classList.add('AnnouncementsText');
            descriptionElement.textContent = announcement.description;

            // Append all elements to the form
            submitButton.appendChild(titleElement);
            submitButton.appendChild(descriptionElement);
            announcementForm.appendChild(emailInput);
            announcementForm.appendChild(telephoneInput);
            announcementForm.appendChild(nickNameInput);
            announcementForm.appendChild(imageInput);
            announcementForm.appendChild(submitButton);

            // Add images if they exist
            if (announcement.images && announcement.images.length > 0) {
                const imageContainer = document.createElement('div');
                imageContainer.classList.add('image-container');
                announcement.images.forEach(image => {
                    const imgElement = document.createElement('img');
                    imgElement.src = image.image;
                    imgElement.alt = 'Announcement Image';
                    imgElement.style.maxWidth = '200px';
                    imgElement.style.marginRight = '10px';
                    imageContainer.appendChild(imgElement);
                });
                announcementForm.appendChild(imageContainer);
            }

            // Append the form to the container
            container.appendChild(announcementForm);

            // Event listener for form submission
            announcementForm.addEventListener('submit', function (event) {
                event.preventDefault();  // Prevent default form submission

                // Collect data from the form
                const title = encodeURIComponent(announcement.title || 'No Title');
                const text = encodeURIComponent(announcement.description || 'No Description');
                const email = encodeURIComponent(announcement.contactEmail || 'No Email');
                const telephone = encodeURIComponent(announcement.contactPhone || 'No Phone');
                const nickName = encodeURIComponent(announcement.nickName || 'Unknown User');

                // Collect all image URLs from hidden inputs (only if images exist)
                const images = announcement.images.map(img => encodeURIComponent(img.image));

                // Prepare the URL for redirection
                const imagesParam = images.length > 0 ? `&images=${images.join(',')}` : '';
                const redirectUrl = `/announcementPage.html?title=${title}&text=${text}&email=${email}&telephone=${telephone}&nickName=${nickName}${imagesParam}`;

                // Redirect to the announcement page with the query parameters
                console.log('Redirecting to:', redirectUrl);
                window.location.href = redirectUrl;
            });
        });
    }

    // Function to update the visibility of the pagination buttons
    function updatePaginationVisibility(currentPage, totalPages) {
        const nextButton = document.querySelector('.pagesButtons.next');
        const prevButton = document.querySelector('.pagesButtons.prev');

        // Show/Hide the Previous button based on currentPage
        if (currentPage === 0) {
            prevButton.style.display = 'none';  // Hide Previous button on first page
        } else {
            prevButton.style.display = 'inline-block';  // Show Previous button on pages > 1
        }

        // Show/Hide the Next button based on currentPage
        if (currentPage === totalPages - 1) {
            nextButton.style.display = 'none';  // Hide Next button on last page
        } else {
            nextButton.style.display = 'inline-block';  // Show Next button on pages < totalPages
        }
    }

    // Event listener for pagination buttons (previous and next)
    document.querySelector('.pagesButtons.next').addEventListener('click', function () {
        if (currentPage < totalPages - 1) {
            console.log('Next button clicked');
            currentPage++;
            fetchAnnouncements(currentPage);
        }
    });

    document.querySelector('.pagesButtons.prev').addEventListener('click', function () {
        if (currentPage > 0) {
            console.log('Previous button clicked');
            currentPage--;
            fetchAnnouncements(currentPage);
        }
    });

    // Initial fetch of the first page of announcements
    fetchAnnouncements(currentPage);
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
