document.getElementById('loginForm').addEventListener('submit', function(event) () {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    const usernameRegex = /^[a-zA-Z0-9._-]{3,20}$/; // Username: 3-20 characters, alphanumeric and ._- allowed. It must be 3-20 characters long and can only include letters, numbers, dots, underscores, or hyphens.
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/; // Password: Minimum 8 characters, at least 1 letter and 1 number
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

        if (!username || !password || !email) {
            showNotification("Please fill in all fields.");
            event.preventDefault();
            return;
        }
        if (!usernameRegex.test(username)) {
            showNotification("Invalid username. 3-20 characters");
            event.preventDefault();
            return;
        }

        if (!passwordRegex.test(password)) {
            showNotification("Invalid password. Minimum 8 characters, at least 1 letter and 1 number");
            event.preventDefault();
            return;
        }
        if (!emailRegex.test(email)) {
            showNotification("Invalid email.");
            event.preventDefault();
            return;
        }

        submitButton.disabled = true;
        submitButton.textContent = 'Submitting...';
});
document.getElementById('password').addEventListener('keydown', function(event) {
        if (event.key === 'Enter') {
            document.getElementById('loginForm').dispatchEvent(new Event('submit'));
        }
    });
document.getElementById("submitButton").addEventListener("click", function(event) {
    if (event.button === 0) {
        document.getElementById('loginForm').dispatchEvent(new Event('submit'));
    }
});
function showNotification(message) {
    const notification = document.getElementById('notification');
    const notificationText = document.getElementById('notification-text');

    notificationText.textContent = message;

    notification.style.display = 'block';

    setTimeout(() => {
        notification.style.display = 'none';
    }, 5000);
}

// Adding animation to button click
const loginButton = document.querySelector('.btn');
loginButton.addEventListener('mousedown', function() {
    loginButton.style.transform = 'scale(0.95)';
});
loginButton.addEventListener('mouseup', function() {
    loginButton.style.transform = 'scale(1)';
});
loginButton.addEventListener('mouseleave', function() {
    loginButton.style.transform = 'scale(1)';
});