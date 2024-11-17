document.addEventListener('DOMContentLoaded', function () {
    // Select all forms with the 5 specific classes
    const forms = document.querySelectorAll(
        '.mainPageAnnouncement1Div, .mainPageAnnouncement2Div, .mainPageAnnouncement3Div'
    );

    // Loop through each form
    forms.forEach(function(form) {
        // Find the title and text inside the form
        const titleElement = form.querySelector('.MainPageAnnouncementHeader');
        const textElement = form.querySelector('.MainPageAnnouncementText');

        // Add an event listener to intercept the form submission
        form.addEventListener('submit', function(event) {
            // Prevent the default form submission
            event.preventDefault();

            // Extract the text from <h2> and <p>
            const title = encodeURIComponent(titleElement.textContent.trim());
            const text = encodeURIComponent(textElement.textContent.trim());

            // Construct the URL with query parameters
            const actionUrl = form.action + `?title=${title}&text=${text}`;

            // Redirect to the new URL with the query parameters
            window.location.href = actionUrl;
        });
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
