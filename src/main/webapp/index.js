document.addEventListener('DOMContentLoaded', function () {
    // Fetch the latest 5 announcements from the backend API
    fetch('/api/announcements/top5')
        .then(response => {
            if (!response.ok) {
                throw new Error('No announcements found');
            }
            return response.json();
        })
        .then(data => {
            const forms = [
                document.querySelector('.mainPageAnnouncement1Div'),
                document.querySelector('.mainPageAnnouncement2Div'),
                document.querySelectorAll('.mainPageAnnouncement3Div')[0],
                document.querySelectorAll('.mainPageAnnouncement3Div')[1],
                document.querySelectorAll('.mainPageAnnouncement3Div')[2]
            ];

            console.log('Forms:', forms);  // Check if forms are correctly selected
            // Populate each form with fetched data
            forms.forEach((form, index) => {
                if (form && data[index]) {
                    const announcement = data[index];

                    // Set text content
                    const titleElement = form.querySelector('.MainPageAnnouncementHeader');
                    const textElement = form.querySelector('.MainPageAnnouncementText');
                    if (titleElement) titleElement.textContent = announcement.title || 'No Title';
                    if (textElement) textElement.textContent = announcement.description || 'No Description';

                    // Store the announcement ID in data-id (using adId here)
                    form.setAttribute('data-id', announcement.adId); // <-- Use adId instead of id
                    console.log(`Setting data-id for form ${index}:`, announcement.adId); // Debugging log
                }
            });

            // Handle form submissions (click on button)
            forms.forEach(function (form) {
                const button = form.querySelector('.AnnouncementButton');
                if (button) {
                    button.addEventListener('click', function (event) {
                        event.preventDefault(); // Prevent form submission

                        const announcementId = form.getAttribute('data-id');
                        console.log('Redirecting to announcement page with adId:', announcementId);  // Log the ID
                        if (announcementId) {
                            // Redirect with only the announcement ID
                            window.location.href = `/announcementPage.html?adId=${announcementId}`;
                        } else {
                            console.error('No ID found for this announcement');
                        }
                    });
                }
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
