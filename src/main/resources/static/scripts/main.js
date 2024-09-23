document.addEventListener('DOMContentLoaded', function() {
    const menuToggle = document.querySelector('.menu-toggle');
    const menuContent = document.querySelector('.nav-menu-content');
    const burgerMenu = document.querySelector('.burger-menu');
    const userActions = document.querySelector('.user-actions');

    menuToggle.addEventListener('click', function() {
        menuContent.classList.toggle('open');
    });
    burgerMenu.addEventListener('click', function() {
        userActions.classList.toggle('open');
});
});
