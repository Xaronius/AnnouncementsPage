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

const form = document.getElementById('uploadForm');
const imageInput = document.getElementById('imageInput');
const preview = document.getElementById('preview');
const statusText = document.getElementById('status');

imageInput.addEventListener('change', function () {
    const file = imageInput.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function (e) {
            preview.src = e.target.result;
            preview.style.display = 'block';
        };
        reader.readAsDataURL(file);
    }
});

form.addEventListener('submit', async function (event) {
    event.preventDefault();

    const file = imageInput.files[0];
    if (!file) {
        statusText.textContent = 'Please select a file!';
        return;
    }

    const formData = new FormData();
    formData.append('image', file);

    try {
        const response = await fetch('/upload', {
            method: 'POST',
            body: formData
        });

        const result = await response.json();
        if (response.ok) {
            statusText.textContent = 'Image uploaded successfully!';
        } else {
            statusText.textContent = `Error: ${result.message}`;
        }
    } catch (error) {
        statusText.textContent = 'Error uploading image.';
        console.error('Error:', error);
    }
});
