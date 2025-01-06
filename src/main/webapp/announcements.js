document.addEventListener('DOMContentLoaded', function () {
    // Select all forms with the class "AnnouncementsDiv"
    const forms = document.querySelectorAll('.AnnouncementsDiv');

    forms.forEach(function (form) {
        const titleElement = form.querySelector('.AnnouncementsTitle');  // Select AnnouncementsTitle for the title
        const textElement = form.querySelector('.AnnouncementsText');   // Select AnnouncementsText for the text
        const emailElement = form.querySelector('input[name="email"]'); // Hidden email input
        const telephoneElement = form.querySelector('input[name="telephone"]'); // Hidden telephone input
        const nickNameElement = form.querySelector('input[name="nickName"]');
        const imageElement = form.querySelector('input[name="image"]'); // Hidden image input

        form.addEventListener('submit', function (event) {
            // Prevent the default form submission
            event.preventDefault();

            // Extract values
            const title = encodeURIComponent(titleElement.textContent.trim());
            const text = encodeURIComponent(textElement.textContent.trim());
            const email = encodeURIComponent(emailElement.value);
            const telephone = encodeURIComponent(telephoneElement.value);
            const nickName = nickNameElement ? encodeURIComponent(nickNameElement.value) : '';
            const image = encodeURIComponent(imageElement.value);

            // Construct the URL with query parameters
            const redirectUrl = `/announcementPage.html?title=${title}&text=${text}&email=${email}&telephone=${telephone}&nickName=${nickName}&image=${image}`;

            // Log the redirect URL for debugging
            console.log('Redirecting to:', redirectUrl);

            // Redirect the user to the new page
            window.location.href = redirectUrl;
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
