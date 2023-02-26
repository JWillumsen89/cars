function showMembersPage() {
    // Fetch the members data from the API endpoint
    fetch('api/members?includeAll=true&includeReservations=true')
        .then(response => response.json())
        .then(members => {
            // Create a table to display the members and their reservations
            // Update the content div with the table
            mainContent.innerHTML = createMembersTable(members);
        })
        .catch(error => {
            console.error('Error fetching members:', error);
            mainContent.innerHTML = '<p>Failed to fetch members data.</p>';
        });
}

function createMembersTable(members) {
    return `<table>
                                          <tr>
                                              <th>Username</th>
                                              <th>Email</th>
                                              <th>First Name</th>
                                              <th>Last Name</th>
                                              <th>Street</th>
                                              <th>City</th>
                                              <th>Reservations</th>
                                          </tr>
                                          ${members.map(member => {
          //Puts reservationdata into table
        const reservationsHtml = createReservationData(member);

        return `<tr>
                                                  <td>${member.username}</td>
                                                  <td>${member.email}</td>
                                                  <td>${member.firstName}</td>
                                                  <td>${member.lastName}</td>
                                                  <td>${member.street}</td>
                                                  <td>${member.city}</td>
                                                  <td>${reservationsHtml}</td>
                                              </tr>`;
    }).join('')}
                                 </table>`;
}

function createReservationData(member) {
    if (member.reservations && member.reservations.length > 0) {
        return `<ul>${member.reservations.map(reservation => `<li>
                                                  ID: ${reservation.carId}, 
                                                  Brand: ${reservation.brand}, 
                                                  Model: ${reservation.model}, 
                                                  Rental Date: ${reservation.rentalDate}
                                                  </li>`).join('')}</ul>`;
    }
    return '';
}