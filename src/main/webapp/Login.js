document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form submission

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Enhanced validation
    const usernameRegex = /^[a-zA-Z0-9._-]{3,20}$/; // Username: 3-20 characters, alphanumeric and ._- allowed. It must be 3-20 characters long and can only include letters, numbers, dots, underscores, or hyphens.
    const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/; // Password: Minimum 8 characters, at least 1 letter and 1 number

    if (!username || !password) {
        showNotification("Please fill in both fields.");
        return;
    }

});

// Adding enter key listener for smoother UX
document.getElementById('password').addEventListener('keydown', function(event) {
    if (event.key === 'Enter') {
        document.getElementById('loginForm').dispatchEvent(new Event('submit'));
    }
});

document.getElementById("submitButton").addEventListener("click", function() {
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
