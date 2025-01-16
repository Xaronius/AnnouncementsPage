document.addEventListener('DOMContentLoaded', function () {
    let currentPage = 0;
    const pageSize = 10;
    let totalAnnouncements = 0;
    let totalPages = 0;
    let selectedCategoryId = '';  // Holds the selected category ID

    // Fetch available categories for the dropdown
    function fetchCategories() {
        fetch('/api/announcements/categories')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch categories');
                }
                return response.json();
            })
            .then(categories => {
                console.log('Fetched categories:', categories);  // Debug log
                const dropdown = document.getElementById('categorySelect');

                categories.forEach(category => {
                    const option = document.createElement('option');
                    option.value = category.id;
                    option.textContent = category.name;
                    dropdown.appendChild(option);
                });
            })
            .catch(error => console.error('Error fetching categories:', error));  // Log the error here
    }

    // Fetch announcements with optional category filter
    function fetchAnnouncements(page) {
        let url = `/api/announcements/list?page=${page}&size=${pageSize}`;

        // Append category filter if selected
        if (selectedCategoryId) {
            url += `&categoryId=${selectedCategoryId}`;
        }

        fetch(url)
            .then(response => response.json())
            .then(data => {
                const announcements = data.content || [];
                totalAnnouncements = data.totalElements || 0;
                totalPages = data.totalPages || 1;

                displayAnnouncements(announcements);
                updatePaginationVisibility(currentPage, totalPages);
            })
            .catch(error => console.error('Error fetching announcements:', error));
    }

    // Handle category selection change
    document.querySelector('#categorySelect').addEventListener('change', function () {
        selectedCategoryId = this.value;
        currentPage = 0;  // Reset to the first page
        fetchAnnouncements(currentPage);
    });

    function displayAnnouncements(announcements) {
        const container = document.querySelector('#announcements-container');
        container.innerHTML = '';

        announcements.forEach(announcement => {
            const announcementDiv = document.createElement('div');
            announcementDiv.classList.add('AnnouncementsDiv');
            announcementDiv.style.cursor = 'pointer';  // Make it look clickable

            const titleElement = document.createElement('h2');
            titleElement.classList.add('AnnouncementsTitle');
            titleElement.textContent = announcement.title;

            const descriptionElement = document.createElement('p');
            descriptionElement.classList.add('AnnouncementsText');
            descriptionElement.textContent = announcement.description;

            announcementDiv.appendChild(titleElement);
            announcementDiv.appendChild(descriptionElement);

            // Click event: Redirect to the announcement page with adId
            announcementDiv.addEventListener('click', function () {
                window.location.href = `/announcementPage.html?adId=${announcement.id}`;
            });

            if (announcement.images && announcement.images.length > 0) {
                const imageContainer = document.createElement('div');
                imageContainer.classList.add('image-container');

                const imgElement = document.createElement('img');
                imgElement.src = 'data:image/jpeg;base64,' + announcement.images[0];  // Show the first image as preview
                imgElement.alt = 'Announcement Image';
                imgElement.style.maxWidth = '200px';
                imgElement.style.marginRight = '10px';

                imageContainer.appendChild(imgElement);
                announcementDiv.appendChild(imageContainer);
            }

            container.appendChild(announcementDiv);
        });
    }

    function updatePaginationVisibility(currentPage, totalPages) {
        const nextButton = document.querySelector('.pagesButtons.next');
        const prevButton = document.querySelector('.pagesButtons.prev');

        prevButton.style.display = currentPage === 0 ? 'none' : 'inline-block';
        nextButton.style.display = currentPage === totalPages - 1 ? 'none' : 'inline-block';
    }

    // Pagination buttons
    document.querySelector('.pagesButtons.next').addEventListener('click', function () {
        if (currentPage < totalPages - 1) {
            currentPage++;
            fetchAnnouncements(currentPage);
        }
    });

    document.querySelector('.pagesButtons.prev').addEventListener('click', function () {
        if (currentPage > 0) {
            currentPage--;
            fetchAnnouncements(currentPage);
        }
    });

    // Initialize the page
    fetchCategories();      // Load categories into dropdown
    fetchAnnouncements(0);  // Load all announcements initially
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
