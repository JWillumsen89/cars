//Adds buttons to the navbar
let navbar = '<nav>'
navbar += '<button id="home-btn">Home</button>';
navbar += '<button id="cars-btn">Cars</button>';
navbar += '<button id="members-btn">Members</button>';
navbar += '</nav>';
const headerContent = document.getElementById('navbar-content');
headerContent.innerHTML = navbar;

// Add event listeners to the navbar buttons
document.getElementById('home-btn').addEventListener('click', showHomePage);
document.getElementById('cars-btn').addEventListener('click', showCarsPage);
document.getElementById('members-btn').addEventListener('click', showMembersPage);