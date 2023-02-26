const mainContent = document.getElementById('main-content');
// Show the home page by default
showHomePage();
function showHomePage() {
    mainContent.innerHTML = `<h1 id="welcome-h1">Welcome to this website</h1>`;
}
