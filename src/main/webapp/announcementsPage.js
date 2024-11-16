/*
document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('announcementForm');
    const titleElement = form.querySelector('.AnnouncementsTitle');
    const textElement = form.querySelector('.AnnouncementsText');

    form.addEventListener('submit', function (event) {
        // Prevent the default form submission
        event.preventDefault();

        // Extract text from <h2> and <p>
        const title = encodeURIComponent(titleElement.textContent.trim());
        const text = encodeURIComponent(textElement.textContent.trim());

        // Redirect with query parameters
        window.location.href = form.action + `?title=${title}&text=${text}`;
    });
});
*/
document.addEventListener('DOMContentLoaded', function () {
    // Select all forms with the class "AnnouncementsDiv"
    const forms = document.querySelectorAll('.AnnouncementsDiv');

    forms.forEach(function (form) {
        const titleElement = form.querySelector('.AnnouncementsTitle');
        const textElement = form.querySelector('.AnnouncementsText');

        form.addEventListener('submit', function (event) {
            // Prevent the default form submission
            event.preventDefault();

            // Extract text from <h2> and <p>
            const title = encodeURIComponent(titleElement.textContent.trim());
            const text = encodeURIComponent(textElement.textContent.trim());

            // Redirect with query parameters
            window.location.href = form.action + `?title=${title}&text=${text}`;
        });
    });
});