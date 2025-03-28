document.addEventListener('DOMContentLoaded', function () {
    const resetEmailBtn = document.getElementById('reset-email-btn');
    const emailResetForm = document.getElementById('email-reset-form');
    const resetEmailForm = document.getElementById('reset-email-form');
    const statusMessage = document.getElementById('status-message');
    
    // Show email reset form when reset button is clicked
    resetEmailBtn.addEventListener('click', function () {
        emailResetForm.style.display = 'block';
    });

    // Handle email reset form submission
    resetEmailForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const oldEmail = document.getElementById('old-email').value;
        const newEmail = document.getElementById('new-email').value;
        const confirmEmail = document.getElementById('confirm-email').value;

        // Check if new emails match
        if (newEmail !== confirmEmail) {
            statusMessage.textContent = "New emails do not match!";
            return;
        }

        const payload = new URLSearchParams();
        payload.append('oldEmail', oldEmail);
        payload.append('newEmail', newEmail);

        try {
            const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

            // Debugging - log the CSRF values to ensure they're correct
            console.log("CSRF Token:", csrfToken);
            console.log("CSRF Header:", csrfHeader);

            const response = await fetch('/user/resetEmail', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    [csrfHeader]: csrfToken,
                },
                body: payload,
            });

            // Handle the response
            let result;
            try {
                result = await response.json(); // Try to parse as JSON
            } catch (e) {
                // If JSON parsing fails, read as plain text
                result = await response.text();
                console.error('Non-JSON response:', result);
            }

            if (response.ok) {
                statusMessage.textContent = "Email updated successfully!";
            } else {
                const errorMessage = result.message || result || "Failed to update email";
                statusMessage.textContent = `Error: ${errorMessage}`;
            }
        } catch (error) {
            statusMessage.textContent = 'Error resetting email.';
            console.error('Error:', error);
        }
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
