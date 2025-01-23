document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('updateAnnouncementForm');
    const imageInput = document.getElementById('imageInput');
    const previewContainer = document.getElementById('previewContainer');
    const categorySelect = document.getElementById('categorySelect');
    const statusText = document.getElementById('status');

    const urlParams = new URLSearchParams(window.location.search);
    const adId = urlParams.get('id');

    if (!adId) {
        console.error("Invalid announcementId");
        alert("Invalid announcement ID. Please try again.");
        return;
    }

    console.log("announcement id = " + adId);

    let announcementData;

    fetch(`/api/announcements/${adId}`)
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch announcement data');
            return response.json();
        })
        .then(data => {
            announcementData = data;
            document.getElementById('title').value = data.title;
            document.getElementById('announcementText').value = data.description;
            document.getElementById('email').value = data.contactEmail;
            document.getElementById('telephone').value = data.contactPhone;

            if (data.images && data.images.length > 0) {
                previewContainer.innerHTML = '';
                data.images.forEach(imageBase64 => {
                    const imgElement = document.createElement('img');
                    imgElement.src = `data:image/jpeg;base64,${imageBase64}`;
                    imgElement.alt = 'Current Image';
                    imgElement.style.maxWidth = '200px';
                    imgElement.style.margin = '10px';
                    previewContainer.appendChild(imgElement);
                });
            }

            return fetch('/api/announcements/categories');
        })
        .then(response => {
            if (!response.ok) throw new Error('Failed to fetch categories');
            return response.json();
        })
        .then(categories => {
            categories.forEach(category => {
                const option = document.createElement('option');
                option.value = category.id;
                option.textContent = category.name;
                if (announcementData.category === category.id) {
                    option.selected = true;
                }
                categorySelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Failed to load data. Please try again.');
        });

    imageInput.addEventListener('change', function () {
        previewContainer.innerHTML = '';
        const files = imageInput.files;

        Array.from(files).forEach(file => {
            const reader = new FileReader();
            reader.onload = function (e) {
                const imgElement = document.createElement('img');
                imgElement.src = e.target.result;
                imgElement.alt = 'New Image';
                imgElement.style.maxWidth = '200px';
                imgElement.style.margin = '10px';
                previewContainer.appendChild(imgElement);
            };
            reader.readAsDataURL(file);
        });
    });

    form.addEventListener('submit', async function (event) {
        event.preventDefault();

        const title = document.getElementById('title').value;
        const description = document.getElementById('announcementText').value;
        const email = document.getElementById('email').value;
        const telephone = document.getElementById('telephone').value;
        const category = categorySelect.value;

        const files = imageInput.files;
        const base64Images = await Promise.all(
            Array.from(files).map(file => {
                return new Promise((resolve, reject) => {
                    const reader = new FileReader();
                    reader.onload = function (e) {
                        resolve(e.target.result.split(',')[1]);
                    };
                    reader.onerror = reject;
                    reader.readAsDataURL(file);
                });
            })
        );

        const payload = {
            title,
            description,
            contact_email: email,
            contact_phone: telephone,
            category,
        };

        // Include images only if new images are selected
        if (base64Images.length > 0) {
            payload.images = base64Images;
        }

        console.log("Payload:", payload);

        fetch(`/api/announcements/update/${adId}`, { // Corrected URL
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(payload),
        })
            .then(response => {
                if (response.ok) {
                    alert('Announcement updated successfully!');
                    window.location.href = '/MyAnnouncements';
                } else {
                    response.json().then(data => {
                        alert(`Error: ${data.error || 'Failed to update announcement'}`);
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Failed to update announcement. Please try again.');
            });
    });
});
