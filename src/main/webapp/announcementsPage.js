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
