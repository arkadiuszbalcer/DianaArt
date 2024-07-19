function openModal(image) {
    var modal = document.getElementById("imageModal");
    var modalImage = document.getElementById("modalImage");
    var captionText = document.getElementById("caption");
    modal.style.display = "block";
    modalImage.src = image.src;
    captionText.innerHTML = image.alt;
}

function closeModal() {
    var modal = document.getElementById("imageModal");
    modal.style.display = "none";
}

// Dodaj event listener dla zamknięcia modala kliknięciem poza modalem
window.addEventListener('click', function(event) {
    var modal = document.getElementById("imageModal");
    if (event.target === modal) {
        closeModal();
    }
});

function updatePrice() {
    var quantityInput = document.getElementById("quantity");
    var productPriceElement = document.querySelector(".product-price");
    var totalPriceElement = document.getElementById("totalPrice");

    // Pobierz wartość jednostkową
    var unitPrice = parseFloat(productPriceElement.getAttribute("data-unit-price"));

    // Pobierz ilość
    var quantity = parseInt(quantityInput.value);

    // Oblicz nową cenę
    if (!isNaN(unitPrice) && !isNaN(quantity)) {
        var newPrice = unitPrice * quantity;
        totalPriceElement.textContent = newPrice.toFixed(2) + " zł";
    } else {
        totalPriceElement.textContent = "Błąd";
    }
}

// Funkcja zwiększająca ilość
function increaseQuantity() {
    var quantityInput = document.getElementById("quantity");
    var currentQuantity = parseInt(quantityInput.value);
    if (!isNaN(currentQuantity)) {
        quantityInput.value = currentQuantity + 1;
        updatePrice(); // Aktualizacja ceny
    }
}

// Funkcja zmniejszająca ilość
function decreaseQuantity() {
    var quantityInput = document.getElementById("quantity");
    var currentQuantity = parseInt(quantityInput.value);
    if (!isNaN(currentQuantity) && currentQuantity > 1) {
        quantityInput.value = currentQuantity - 1;
        updatePrice(); // Aktualizacja ceny
    }
}

// Funkcja wywoływana po załadowaniu dokumentu
document.addEventListener("DOMContentLoaded", function() {
    // Inicjalizacja ceny
    updatePrice();
});
