//Gets the specific tag for navbar.
const navbarContent = document.getElementById('navbar-content');

function createButton(id, text, onclick) {
    const button = document.createElement('button');
    button.id = id;
    button.textContent = text;
    button.addEventListener('click', onclick);
    return button;
}

//Adds buttons to navbar
navbarContent.appendChild(createButton('home-btn', 'Home', showHomePage));
navbarContent.appendChild(createButton('cars-btn', 'Cars', showCarsPage));
navbarContent.appendChild(createButton('members-btn', 'Members', showMembersPage));
