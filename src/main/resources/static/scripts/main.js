document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.querySelector('.menu-toggle');
    const menuContent = document.querySelector('.nav-menu-content');

    menuToggle.addEventListener('click', function() {
        menuContent.classList.toggle('open');
    });
});
