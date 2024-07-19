// scripts/script.js

// JavaScript for sidebar functionality

document.addEventListener('DOMContentLoaded', function () {
    const sidebar = document.querySelector('.sidebar');

    // Function to show the sidebar
    function showSidebar() {
        sidebar.style.left = '0';
        document.body.classList.add('sidebar-active');
    }

    // Function to hide the sidebar
    function hideSidebar() {
        sidebar.style.left = '-60px'; // Adjust according to sidebar width
        document.body.classList.remove('sidebar-active');
    }

    // Show sidebar when mouse enters the sidebar area
    sidebar.addEventListener('mouseenter', showSidebar);

    // Hide sidebar when mouse leaves the sidebar area
    sidebar.addEventListener('mouseleave', hideSidebar);

    // Optional: Toggle sidebar visibility on button click or other actions
    // const toggleButton = document.querySelector('.toggle-button');
    // toggleButton.addEventListener('click', function () {
    //   if (sidebar.style.left === '0px') {
    //     hideSidebar();
    //   } else {
    //     showSidebar();
    //   }
    // });
});
