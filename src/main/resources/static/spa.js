
//Adds buttons to the navbar
let navbarBtns = '<button id="home-btn">Home</button>';
navbarBtns += '<button id="cars-btn">Cars</button>';
navbarBtns += '<button id="members-btn">Members</button>';
const navbar = document.getElementById('navbar');
navbar.innerHTML = navbarBtns;


// Add event listeners to the navbar buttons
const homeBtn = document.getElementById('home-btn');
homeBtn.addEventListener('click', showHomePage);
const carsBtn = document.getElementById('cars-btn');
carsBtn.addEventListener('click', showCarsPage);
const membersBtn = document.getElementById('members-btn');
membersBtn.addEventListener('click', showMembersPage);

// Show the home page by default
showHomePage();

// Function to delete a car by ID
function deleteCar(id) {
    // Fetch the car data
    fetch(`api/cars/${id}`)
        .then(response => response.json())
        .then(car => {
            // Create an overlay div to disable clicking outside the modal
            const overlayDiv = document.createElement('div');
            overlayDiv.classList.add('overlay');
            document.body.appendChild(overlayDiv);

            // Show a confirmation popup
            const confirmPopup = document.createElement('div');
            confirmPopup.classList.add('confirm-popup');
            confirmPopup.innerHTML = `
        <h3>Are you sure you want to delete this car?</h3>
        <p>Brand: ${car.brand}</p>
        <p>Model: ${car.model}</p>
        <p>Price Per Day: ${car.pricePrDay}</p>
        <p>Best Discount: ${car.bestDiscount}</p>
        <button class="confirm-delete" data-id="${id}">Delete</button>
        <button class="cancel-delete">Cancel</button>
      `;
            document.body.appendChild(confirmPopup);

            // Add event listeners to the confirm/cancel buttons
            const confirmBtn = confirmPopup.querySelector('.confirm-delete');
            confirmBtn.addEventListener('click', function () {
                // Send the delete request to the API
                fetch(`api/cars/${id}`, {
                    method: 'DELETE'
                })
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Failed to delete car');
                        }
                        // Reload the cars page
                        showCarsPage();
                    })
                    .catch(error => {
                        console.error('Error deleting car:', error);
                    });
                // Remove the popup window and overlay
                confirmPopup.remove();
                overlayDiv.remove();
            });

            const cancelBtn = confirmPopup.querySelector('.cancel-delete');
            cancelBtn.addEventListener('click', function () {
                // Remove the popup window and overlay
                confirmPopup.remove();
                overlayDiv.remove();
            });
        })
        .catch(error => {
            console.error('Error fetching car data:', error);
        });
}


function showHomePage() {
    const contentDiv = document.getElementById('content');
    contentDiv.innerHTML = '<h1>Welcome to this website</h1>';
}

function showCarsPage() {
    const contentDiv = document.getElementById('content');

    // Fetch the cars data from the API endpoint
    fetch('api/cars')
        .then(response => response.json())
        .then(cars => {
            // Create a table to display the cars
            let tableHtml = '<table>';
            tableHtml += '<tr><th>ID</th><th>Brand</th><th>Model</th><th>Price Per Day</th><th>Best Discount</th><th></th></tr>';
            for (let car of cars) {
                tableHtml += `<tr><td>${car.id}</td><td>${car.brand}</td><td>${car.model}</td><td>${car.pricePrDay}</td><td>${car.bestDiscount}</td><td><button class="delete-car" data-id="${car.id}">Delete</button></td></tr>`;
            }
            tableHtml += '</table>';

            // Create a form to add a new car
            let formHtml = '<div id="add-car-modal">';
            formHtml += '<div id="add-car-modal-content">';
            formHtml += '<span class="close">&times;</span>';
            formHtml += '<form id="add-car-form">';
            formHtml += '<h2>Add a new car</h2>';
            formHtml += '<label for="brand-input">Brand:</label>';
            formHtml += '<input type="text" id="brand-input" name="brand"><br>';
            formHtml += '<label for="model-input">Model:</label>';
            formHtml += '<input type="text" id="model-input" name="model"><br>';
            formHtml += '<label for="price-input">Price Per Day:</label>';
            formHtml += '<input type="number" id="price-input" name="pricePrDay"><br>';
            formHtml += '<label for="discount-input">Best Discount:</label>';
            formHtml += '<input type="number" id="discount-input" step="0.1" name="bestDiscount"><br>';
            formHtml += '<button type="submit">Add Car</button>';
            formHtml += '</form>';
            formHtml += '</div>';
            formHtml += '</div>';

            let addCarBtnHtml = '<button id="add-new-car">Add New Car</button>'
            // Combine the table and form HTML
            let pageHtml = addCarBtnHtml + tableHtml + formHtml;

            // Update the content div with the combined HTML
            contentDiv.innerHTML = pageHtml;

            // Add event listener to the form submission
            const form = document.getElementById('add-car-form');
            form.addEventListener('submit', handleAddCar);

            // Add event listener to the "Add New Car" button
            const addCarBtn = document.getElementById('add-new-car');
            const addCarModal = document.getElementById('add-car-modal');
            const addCarModalContent = document.getElementById('add-car-modal-content');
            const closeBtn = document.getElementsByClassName('close')[0];
            const table = document.querySelector('table');

            addCarBtn.addEventListener('click', function () {
                // Show the modal
                addCarModal.style.display = 'block';
            });
            // Close the modal when the user clicks on the close button

            closeBtn.addEventListener('click', function () {
                addCarModal.style.display = 'none';
            });
            // Close the modal when the user clicks outside the modal content

            window.addEventListener('click', function (event) {
                if (event.target === addCarModal) {
                    addCarModal.style.display = 'none';
                }
            });
            table.addEventListener('click', function (event) {
                if (event.target.classList.contains('delete-car')) {
                    const id = event.target.getAttribute('data-id');
                    deleteCar(id);
                }
            });
        })

        .catch(error => {
            console.error('Error fetching cars:', error);
            contentDiv.innerHTML = '<p>Failed to fetch cars data.</p>';
        });
    // Add event listener to the table for handling delete clicks

}


function handleAddCar(event) {
    event.preventDefault();

    // Get the input values from the form
    const brandInput = document.getElementById('brand-input');
    const modelInput = document.getElementById('model-input');
    const priceInput = document.getElementById('price-input');
    const discountInput = document.getElementById('discount-input');


    // Create the car object to send to the API
    const car = {
        brand: brandInput.value,
        model: modelInput.value,
        pricePrDay: priceInput.value,
        bestDiscount: discountInput.value,
    };

    // Send the new car to the API endpoint
    fetch('api/cars', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(car),
    })
        .then(response => response.json())
        .then(newCar => {
            // Reload the cars table to show the new car
            showCarsPage();
        })
        .catch(error => {
            console.error('Error adding car:', error);
        });
}


function showMembersPage() {
    const contentDiv = document.getElementById('content');

    // Fetch the members data from the API endpoint
    fetch('api/members?includeAll=true&includeReservations=true')
        .then(response => response.json())
        .then(members => {
            // Create a table to display the members and their reservations
            let tableHtml = '<table>';
            tableHtml += '<tr><th>Username</th><th>Email</th><th>First Name</th><th>Last Name</th><th>Street</th><th>City</th><th>Reservations</th></tr>';
            for (let member of members) {
                let reservationsHtml = '';
                if (member.reservations && member.reservations.length > 0) {
                    reservationsHtml = '<ul>';
                    for (let reservation of member.reservations) {
                        reservationsHtml += `<li>ID: ${reservation.carId}, Brand: ${reservation.brand}, Model: ${reservation.model}, Rental Date: ${reservation.rentalDate}</li>`;
                    }
                    reservationsHtml += '</ul>';
                }
                tableHtml += `<tr><td>${member.username}</td><td>${member.email}</td><td>${member.firstName}</td><td>${member.lastName}</td><td>${member.street}</td><td>${member.city}</td><td>${reservationsHtml}</td></tr>`;
            }
            tableHtml += '</table>';

            // Update the content div with the table

            contentDiv.innerHTML = tableHtml;
        })
        .catch(error => {
            console.error('Error fetching members:', error);
            contentDiv.innerHTML = '<p>Failed to fetch members data.</p>';
        });
}