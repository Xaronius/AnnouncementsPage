document.addEventListener('DOMContentLoaded', function () {
    let currentPage = 0;
    const pageSize = 10;

    // Fetch the announcements
    fetchAnnouncements(currentPage);

    function fetchAnnouncements(page) {
        let url = `/api/announcements/user/2?page=${page}&size=${pageSize}`;  // Modify as needed

        fetch(url)
            .then(response => response.json())
            .then(data => {
                console.log("Fetched Data:", data);  // Log the data to check what is being returned
                const announcements = data.content || [];
                displayAnnouncements(announcements);
            })
            .catch(error => console.error('Error fetching announcements:', error));
    }

    function displayAnnouncements(announcements) {
        const container = document.querySelector('#announcements-container');
        container.innerHTML = '';

        announcements.forEach(announcement => {
            const announcementDiv = document.createElement('div');
            announcementDiv.classList.add('AnnouncementsDiv');
            announcementDiv.style.cursor = 'pointer';
            announcementDiv.style.position = 'relative';  // Make the div's position relative to contain the delete button

            // Add Delete Button
            const deleteButton = document.createElement('button');
            deleteButton.classList.add('delete-button');
            deleteButton.textContent = 'Delete';
            deleteButton.style.position = 'absolute';  // Button will be positioned relative to announcementDiv
            deleteButton.style.top = '10px';
            deleteButton.style.right = '10px';

            // Add delete button event listener
            deleteButton.addEventListener('click', function(event) {
                event.stopPropagation(); // Prevent triggering the redirect
                if (confirm('Are you sure you want to delete this announcement?')) {
                    deleteAnnouncement(announcement.id, announcementDiv);
                }
            });

            // Add Update Button
            const updateButton = document.createElement('button');
            updateButton.classList.add('update-button');
            updateButton.textContent = 'Update';
            updateButton.style.position = 'absolute';
            updateButton.style.bottom = '10px';
            updateButton.style.right = '10px';

            // add update event listener
            updateButton.addEventListener('click', function (event) {
                event.stopPropagation();
                const url = `/UpdateAnnouncement?id=${announcement.id}`; // Ensure this matches the GetMapping
                window.location.href = url;
            });

            const titleElement = document.createElement('h2');
            titleElement.classList.add('AnnouncementsTitle');
            titleElement.textContent = announcement.title;

            const descriptionElement = document.createElement('p');
            descriptionElement.classList.add('AnnouncementsText');
            descriptionElement.textContent = announcement.description;

            announcementDiv.appendChild(deleteButton);
            announcementDiv.appendChild(updateButton);
            announcementDiv.appendChild(titleElement);
            announcementDiv.appendChild(descriptionElement);

            // Click event: Redirect to the announcement page with adId
            announcementDiv.addEventListener('click', function () {
                const url = `/announcementPage?adId=${announcement.id}`;
                window.location.href = url;
            });

            if (announcement.images && announcement.images.length > 0) {
                const imageContainer = document.createElement('div');
                imageContainer.classList.add('image-container');

                const imgElement = document.createElement('img');
                imgElement.src = 'data:image/jpeg;base64,' + announcement.images[0];
                imgElement.alt = 'Announcement Image';
                imgElement.style.maxWidth = '200px';

                imageContainer.appendChild(imgElement);
                announcementDiv.appendChild(imageContainer);
            }

            container.appendChild(announcementDiv);
        });
    }

    // Function to delete an announcement
    function deleteAnnouncement(announcementId, announcementDiv) {
        fetch(`/api/announcements/delete/${announcementId}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    // Remove the announcement div from the page if deletion is successful
                    announcementDiv.remove();
                    alert('Announcement deleted successfully');
                } else {
                    alert('Error deleting the announcement');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error deleting the announcement');
            });
    }
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
