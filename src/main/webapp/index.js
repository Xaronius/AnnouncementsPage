document.addEventListener('DOMContentLoaded', function () {
    const forms = document.querySelectorAll('.mainPageAnnouncement1Div, .mainPageAnnouncement2Div, .mainPageAnnouncement3Div');

    forms.forEach(function (form) {
        const titleElement = form.querySelector('.MainPageAnnouncementHeader');
        const textElement = form.querySelector('.MainPageAnnouncementText');
        const emailElement = form.querySelector('input[name="email"]');
        const telephoneElement = form.querySelector('input[name="telephone"]');
        const nickNameElement = form.querySelector('input[name="nickName"]');
        const imageInput = form.querySelector('input[name="image"]');

        // Listen for form submission
        form.addEventListener('submit', function (event) {
            event.preventDefault();  // Prevent form submission and page reload

            // Get values from the form
            const title = titleElement ? encodeURIComponent(titleElement.textContent.trim()) : '';
            const text = textElement ? encodeURIComponent(textElement.textContent.trim()) : '';
            const email = emailElement ? encodeURIComponent(emailElement.value) : '';
            const telephone = telephoneElement ? encodeURIComponent(telephoneElement.value) : '';
            const nickName = nickNameElement ? encodeURIComponent(nickNameElement.value) : '';
            const image = imageInput ? encodeURIComponent(imageInput.value) : '';

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
