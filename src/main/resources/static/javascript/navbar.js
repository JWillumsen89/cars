//Adds buttons to the navbar
const navbarContent = document.getElementById('navbar-content');
navbarContent.innerHTML =  `
 <nav>
<button id="home-btn">Home</button>
<button id="cars-btn">Cars</button>
<button id="members-btn">Members</button>
</nav>
`;

// Add event listeners to the navbar buttons
document.getElementById('home-btn').addEventListener('click', showHomePage);
document.getElementById('cars-btn').addEventListener('click', showCarsPage);
document.getElementById('members-btn').addEventListener('click', showMembersPage);