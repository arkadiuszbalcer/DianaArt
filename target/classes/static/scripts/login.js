document.addEventListener('DOMContentLoaded', (event) => {
    const modal = document.getElementById("loginPopup");
    const loginButton = document.getElementById("openLoginBtn");
    const closeButton = document.querySelector(".closeBtn");
    const content = document.getElementById("mainContent"); // Assuming you have an element with this ID

    loginButton.onclick = function() {
        modal.style.display = "block";
        if (content) content.classList.add("blur"); // Adding blur if the element exists
    }

    closeButton.onclick = function() {
        modal.style.display = "none";
        if (content) content.classList.remove("blur"); // Removing blur if the element exists
        history.back();
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
            if (content) content.classList.remove("blur"); // Removing blur if the element exists
            history.back();
        }
    }
    // JavaScript for redirection
    document.getElementById('registerBtn').addEventListener('click', function() {
        window.location.href = '/registration'; // Adjust the URL as needed
    });
});
