function showMembersPage() {
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

            mainContent.innerHTML = tableHtml;
        })
        .catch(error => {
            console.error('Error fetching members:', error);
            mainContent.innerHTML = '<p>Failed to fetch members data.</p>';
        });
}