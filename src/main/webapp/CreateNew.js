document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('createAnnouncementForm');
    const imageInput = document.getElementById('imageInput');
    const previewContainer = document.getElementById('previewContainer');
    const statusText = document.getElementById('status');
    const categorySelect = document.getElementById('categorySelect'); // Correct ID

    // Fetch categories from the backend
    fetch('/api/announcements/categories')  // Correct URL
        .then(response => response.json())
        .then(categories => {
            // Populate the dropdown with categories
            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category.id;  // Use 'id' from response
                option.textContent = category.name;  // Use 'name' from response
                categorySelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error fetching categories:', error);
        });

    // Preview multiple images
    imageInput.addEventListener('change', function () {
        previewContainer.innerHTML = '';

        Array.from(imageInput.files).forEach(file => {
            const reader = new FileReader();
            reader.onload = function (e) {
                const img = document.createElement('img');
                img.src = e.target.result;
                img.alt = 'Preview Image';
                img.style.maxWidth = '100px';
                img.style.margin = '5px';
                previewContainer.appendChild(img);
            };
            reader.readAsDataURL(file);
        });
    });

    // Handle form submission
    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const title = document.getElementById('title').value;
        const description = document.getElementById('announcementText').value;
        const email = document.getElementById('email').value;
        const telephone = document.getElementById('telephone').value;
        const files = imageInput.files;
        const category = categorySelect.value;

        if (files.length === 0) {
            statusText.textContent = 'Please select at least one image!';
            return;
        }

        if (!category) {
            statusText.textContent = 'Please select a category!';
            return;
        }

        // Convert images to Base64
        const base64Images = await Promise.all(
            Array.from(files).map(file => {
                return new Promise((resolve, reject) => {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        resolve(e.target.result.split(',')[1]);  // Only Base64 content
                    };
                    reader.onerror = function () {
                        reject('Error reading file');
                    };
                    reader.readAsDataURL(file);
                });
            })
        );

        const payload = {
            title: title,
            description: description,
            contact_email: email,
            contact_phone: telephone,
            category: category, // Send category ID
            images: base64Images
        };

        // Send JSON data
        try {
            const response = await fetch('/api/announcements/create', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(payload)
            });

            // Check if response is JSON, and parse accordingly
            const result = await response.json();  // Will parse the response body to JSON

            if (response.ok) {
                statusText.textContent = 'Announcement created successfully!';
                form.reset();
                previewContainer.innerHTML = '';
            } else {
                // Handle the error case by showing the error message from the response
                statusText.textContent = `Error: ${result.error || 'An error occurred'}`;
            }
        } catch (error) {
            statusText.textContent = 'Error creating announcement.';
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