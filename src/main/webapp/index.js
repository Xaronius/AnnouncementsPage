
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
        const emailElement = form.querySelector('input[name="email"]');
        const telephoneElement = form.querySelector('input[name="telephone"]');
        const imageElement = form.querySelector('input[name="image"]');

        // Add an event listener to intercept the form submission
        form.addEventListener('submit', function(event) {
            // Prevent the default form submission
            event.preventDefault();

            // Extract the text from <h2> and <p>
            const title = encodeURIComponent(titleElement.textContent.trim());
            const text = encodeURIComponent(textElement.textContent.trim());
            const email = encodeURIComponent(emailElement ? emailElement.value : '');
            const telephone = encodeURIComponent(telephoneElement ? telephoneElement.value : '');
            let actionUrl = form.action + `?title=${title}&text=${text}&email=${email}&telephone=${telephone}`;

            const imageFile = imageElement.files[0];
            if (imageFile) {
                // For image upload, you can't use GET. You need a form submission with POST method.
                const formData = new FormData();
                formData.append('title', title);
                formData.append('text', text);
                formData.append('email', email);
                formData.append('telephone', telephone);
                formData.append('image', imageFile);

                // Use fetch or XMLHttpRequest to submit the data with POST
                fetch(form.action, {
                    method: 'POST',
                    body: formData,
                }).then(response => {
                    // Handle the response here
                    if (response.ok) {
                        window.location.href = '/announcement';  // Redirect after successful submission
                    } else {
                        console.error('Error uploading announcement.');
                    }
                }).catch(error => {
                    console.error('Error:', error);
                });

                return; // Prevent the default form submission (because we handled it manually)
            }

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
