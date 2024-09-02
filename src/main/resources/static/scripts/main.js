
document.addEventListener('DOMContentLoaded', () => {
    const toggle = document.querySelector(".menu-toggle");
    const menu = document.querySelector(".user-actions");

    function toggleMenu() {
        if (menu.classList.contains("expanded")) {
            menu.classList.remove("expanded");
            toggle.querySelector('#toggle-icon').classList.replace('fa-minus-square', 'fa-bars');
        } else {
            menu.classList.add("expanded");
            toggle.querySelector('#toggle-icon').classList.replace('fa-bars', 'fa-minus-square');
        }
    }

    toggle.addEventListener("click", toggleMenu, false);
});
