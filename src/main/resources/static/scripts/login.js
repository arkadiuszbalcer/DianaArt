document.addEventListener('DOMContentLoaded', () => {
    // Get modal and relevant buttons
    const modal = document.getElementById("loginPopup");
    const loginButton = document.getElementById("openLoginBtn");
    const closeButton = document.querySelector(".closeBtn");
    const content = document.getElementById("mainContent"); // Assuming you have an element with this ID
    const registerBtn = document.getElementById('registerBtn');

    // Check if elements exist before adding event listeners
    if (loginButton) {
        loginButton.addEventListener('click', () => {
            if (modal) modal.style.display = "block";
            if (content) content.classList.add("blur");
        });
    }

    if (closeButton) {
        closeButton.addEventListener('click', () => {
            if (modal) modal.style.display = "none";
            if (content) content.classList.remove("blur");
            // Optionally check if history exists before using back
            if (window.history.length > 1) {
                history.back();
            } else {
                window.location.href = '/'; // Redirect to home or fallback page
            }
        });
    }

    if (modal) {
        window.addEventListener('click', (event) => {
            if (event.target === modal) {
                modal.style.display = "none";
                if (content) content.classList.remove("blur");
                if (window.history.length > 1) {
                    history.back();
                } else {
                    window.location.href = '/'; // Redirect to home or fallback page
                }
            }
        });
    }

    if (registerBtn) {
        registerBtn.addEventListener('click', () => {
            window.location.href = '/registration'; // Adjust the URL as needed
        });
    }

    // Function to handle redirection after login or registration
    function handleRedirection() {
        const loginStatus = sessionStorage.getItem('loginStatus');
        const registrationStatus = sessionStorage.getItem('registrationStatus');

        if (loginStatus === 'success') {
            sessionStorage.removeItem('loginStatus'); // Clean up after redirection
            window.location.href = '/'; // Redirect to homepage
        } else if (registrationStatus === 'success') {
            sessionStorage.removeItem('registrationStatus'); // Clean up after redirection
            window.location.href = '/login'; // Redirect to login page
        }
    }

    // Check redirection status when page loads
    handleRedirection();
});
