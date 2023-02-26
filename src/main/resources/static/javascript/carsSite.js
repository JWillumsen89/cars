function showCarsPage() {

    // Fetch the cars data from the API endpoint
    fetch('api/cars')
        .then(response => response.json())
        .then(cars => {
            // Update the content div with the combined HTML
            mainContent.innerHTML = '<button id="add-new-car">Add New Car</button>' + createCarsTable(cars) + createAddCarModal();

            //Opens modal when add new car button is clicked
            document.getElementById('add-new-car').addEventListener('click', () => addCarModal.style.display = 'block');
            //Adds new car, and run handleAddCar function
            document.getElementById('add-car-form').addEventListener('submit', handleAddCar);

            const addCarModal = document.getElementById('add-car-modal');

            document.querySelector('.close').addEventListener('click', () => addCarModal.style.display = 'none');
            window.addEventListener('click', event => event.target === addCarModal && (addCarModal.style.display = 'none'));


            const table = document.querySelector('table');
            table.addEventListener('click', event => {
                if (event.target.classList.contains('delete-car')) {
                    const id = event.target.dataset.id;
                    deleteCar(id);
                }
            });

        })

        .catch(error => {
            console.error('Error fetching cars:', error);
            mainContent.innerHTML = '<p>Failed to fetch cars data.</p>';
        });
}

function createCarsTable(cars) {
    return `
             <table>
                 <tr>
                    <th>ID</th>
                    <th>Brand</th>
                    <th>Model</th>
                    <th>Price Per Day</th>
                    <th>Best Discount</th>
                    <th></th>
                </tr>
              ${cars.map(car => `
                <tr>
                <td>${car.id}</td>
                <td>${car.brand}</td>
                <td>${car.model}</td>
                <td>${car.pricePrDay}</td>
                <td>${car.bestDiscount}</td>
                <td><button class="delete-car" data-id="${car.id}">Delete</button></td>
                </tr>
                    `).join('')}
            </table>`;
}

function createAddCarModal() {
    return `
                  <div id="add-car-modal">
                    <div id="add-car-modal-content">
                      <span class="close">&times;</span>
                      <form id="add-car-form">
                        <h2>Add a new car</h2>
                        <label for="brand-input">Brand:</label>
                        <input type="text" id="brand-input" name="brand" required><br>
                        <label for="model-input">Model:</label>
                        <input type="text" id="model-input" name="model" required><br>
                        <label for="price-input">Price Per Day:</label>
                        <input type="number" id="price-input" name="pricePrDay" required><br>
                        <label for="discount-input">Best Discount:</label>
                        <input type="number" id="discount-input" name="bestDiscount" required><br>
                        <button type="submit">Add Car</button>
                      </form>
                    </div>
                  </div>
                `;
}

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
            const confirmPopup = document.body.appendChild(Object.assign(document.createElement('div'),
                {classList: ['confirm-popup'], innerHTML: `
                                    <h3>Are you sure you want to delete this car?</h3>
                                    <p>Brand: ${car.brand}</p>
                                    <p>Model: ${car.model}</p>
                                    <p>Price Per Day: ${car.pricePrDay}</p>
                                    <p>Best Discount: ${car.bestDiscount}</p>
                                    <button class="confirm-delete" data-id="${id}">Delete</button>
                                    <button class="cancel-delete">Cancel</button>`}));
            
            //Adds event listener to confirm-btn
            confirmPopup.querySelector('.confirm-delete').addEventListener('click', function () {
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
            //Adds event listener to delete-btn
            confirmPopup.querySelector('.cancel-delete').addEventListener('click', function () {
                // Remove the popup window and overlay
                confirmPopup.remove();
                overlayDiv.remove();
            });
        })
        .catch(error => {
            console.error('Error fetching car data:', error);
        });
}

function handleAddCar(event) {
    event.preventDefault();

    const car = {
        brand: document.getElementById('brand-input').value,
        model: document.getElementById('model-input').value,
        pricePrDay: document.getElementById('price-input').value,
        bestDiscount: document.getElementById('discount-input').value,
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