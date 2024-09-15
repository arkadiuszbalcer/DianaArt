document.addEventListener('DOMContentLoaded', () => {
    updateOrder();
    updateButtonStates();
    updateTotalOrderSum();
    initializeProductSearch(); // Inicjalizacja funkcji wyszukiwania
    initializeSorting();

    function initializeSorting() {
        const sortAscButton = document.getElementById('sortAsc');
        const sortDescButton = document.getElementById('sortDesc');
        const productList = document.getElementById('productList');

        if (!productList) return;

        const products = Array.from(productList.getElementsByClassName('product'));

        // Sortuj rosnąco
        sortAscButton.addEventListener('click', () => {
            const sortedProducts = products.sort((a, b) => {
                const priceA = parseFloat(a.querySelector('.product-price span').textContent);
                const priceB = parseFloat(b.querySelector('.product-price span').textContent);
                return priceA - priceB;
            });
            displaySortedProducts(sortedProducts, productList);
        });

        // Sortuj malejąco
        sortDescButton.addEventListener('click', () => {
            const sortedProducts = products.sort((a, b) => {
                const priceA = parseFloat(a.querySelector('.product-price span').textContent);
                const priceB = parseFloat(b.querySelector('.product-price span').textContent);
                return priceB - priceA;
            });
            displaySortedProducts(sortedProducts, productList);
        });
    }

    // Wyświetlanie posortowanych produktów
    function displaySortedProducts(sortedProducts, productList) {
        productList.innerHTML = ''; // Czyścimy listę produktów
        sortedProducts.forEach(product => {
            productList.appendChild(product); // Dodajemy posortowane produkty
        });
    }
});
    // Funkcja do pobierania liczby produktów w koszyku
    function updateCartCount() {
        fetch('/cart/count')
            .then(response => response.json())
            .then(data => {
                const cartCountElement = document.getElementById('cart-count');
                if (cartCountElement) {
                    cartCountElement.textContent = data;
                } else {
                    console.error('Element #cart-count not found');
                }
            })
            .catch(error => {
                console.error('Error fetching cart count:', error);
            });
    }

    // Wywołaj funkcję aktualizacji licznika koszyka po dodaniu produktu do koszyka
    const addToCartForms = document.querySelectorAll('.add-to-cart-form');
    addToCartForms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            const formData = new FormData(form);
            const productId = formData.get('productId');
            const quantity = formData.get('quantity');
            const orderDate = new Date().toISOString();
            const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

            fetch('/shoppingCart/Product', {
                method: 'POST',
                headers: {
                    'X-CSRF-TOKEN': csrfToken,
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    productId: String(productId),
                    quantity: String(quantity),
                    orderDate: orderDate
                })
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Add to cart response:', data);
                    if (data.success) {
                        updateCartCount(); // Aktualizuj licznik koszyka na sukces
                        showMessage('Produkt został pomyślnie dodany do koszyka');
                    } else {
                        showMessage('Produkt nie został dodany do koszyka');
                    }
                })
                .catch(error => {
                    console.error('Error adding product to cart:', error);
                    showMessage('Musisz być zalogowany, aby dodać produkt do koszyka');
                });
        });

    // Inicjalna aktualizacja licznika koszyka
    updateCartCount();
});

    // Update product quantity
    function updateOrder() {
        const updateButtons = document.querySelectorAll('.update-quantity');
        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

        updateButtons.forEach(button => {
            button.addEventListener('click', function () {
                const productId = this.getAttribute('data-product-id');
                const action = this.getAttribute('data-action');
                const quantityElement = this.closest('.product-item').querySelector('.quantity-value');
                let quantity = parseInt(quantityElement.textContent);

                if (action === 'increase') {
                    quantity += 1;
                } else if (action === 'decrease' && quantity > 1) {
                    quantity -= 1;
                }

                fetch('/shoppingCart/Quantity', {
                    method: 'PATCH',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-CSRF-TOKEN': csrfToken
                    },
                    body: new URLSearchParams({ productId, quantity })
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log('Update quantity response:', data); // Log response data
                        if (data.success) {
                            quantityElement.textContent = quantity;
                            updateTotalOrderSum();
                            updateButtonStates();
                        } else {
                            alert('Wystąpił błąd podczas aktualizacji ilości produktu');
                        }
                    })
                    .catch(error => {
                        console.error('Error updating quantity:', error);
                        alert('Wystąpił błąd podczas aktualizacji ilości produktu');
                    });
            });
        });

        // Remove product
        const removeButtons = document.querySelectorAll('.remove-product-form button[type="submit"]');
        removeButtons.forEach(button => {
            button.addEventListener('click', function (event) {
                event.preventDefault();
                const form = this.closest('.remove-product-form');
                const productId = form.querySelector('input[name="productId"]').value;

                fetch(form.action, {
                    method: 'DELETE',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                        'X-CSRF-TOKEN': csrfToken
                    },
                    body: new URLSearchParams({ productId })
                })
                    .then(response => response.json())
                    .then(data => {
                        console.log('Remove product response:', data); // Log response data
                        if (data.success) {
                            form.closest('.product-item').remove();
                            updateTotalOrderSum();
                            updateButtonStates();
                        } else {
                            alert('Wystąpił błąd podczas usuwania produktu');
                        }
                    })
                    .catch(error => {
                        console.error('Error removing product:', error);
                        alert('Wystąpił błąd podczas usuwania produktu');
                    });
            });
        });
    }

    function updateTotalOrderSum() {
        const totalOrderSumElement = document.querySelector('.total-order-sum');
        if (!totalOrderSumElement) {
            console.log('Total order sum element not found on this page.');
            return; // Exit the function if the element is not found
        }

        const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

        fetch('/shoppingCart/totalOrderSum', {
            method: 'GET',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        })
            .then(response => response.json())
            .then(data => {
                console.log('Total order sum response:', data); // Log response data
                totalOrderSumElement.textContent = data.totalOrderSum;
            })
            .catch(error => {
                console.error('Error getting total order sum:', error);
            });
    }

    function updateButtonStates() {
        document.querySelectorAll('.product-item').forEach(item => {
            const quantityElement = item.querySelector('.quantity-value');
            const quantity = parseInt(quantityElement.textContent);
            const decreaseButton = item.querySelector('.update-quantity[data-action="decrease"]');
            if (decreaseButton) {
                decreaseButton.disabled = quantity <= 1;
            }
        });
    }

function initializeProductSearch() {
    const searchInput = document.getElementById('searchInput');
    if (!searchInput) return; // Exit if the search input element is not found

    const productList = document.getElementById('productList');
    if (!productList) return; // Exit if the product list element is not found

    const noResultsMessage = document.getElementById('noResultsMessage'); // Element z komunikatem
    const products = productList.querySelectorAll('.product');
    if (products.length === 0) return; // Exit if no products are found

    searchInput.addEventListener('input', function () {
        const query = searchInput.value.toLowerCase();
        let foundAny = false; // Flaga do śledzenia, czy znaleziono jakiekolwiek produkty

        products.forEach(product => {
            const productName = product.querySelector('h3') ? product.querySelector('h3').textContent.toLowerCase() : '';
            const productPrice = product.querySelector('p') ? product.querySelector('p').textContent.toLowerCase() : '';

            if (productName.includes(query) || productPrice.includes(query)) {
                product.style.display = '';
                foundAny = true; // Zmieniamy flagę, jeśli produkt pasuje do zapytania
            } else {
                product.style.display = 'none';
            }
        });

        // Wyświetl komunikat, jeśli żaden produkt nie pasuje
        if (foundAny) {
            noResultsMessage.style.display = 'none';
        } else {
            noResultsMessage.style.display = 'block';
        }
    });
}


function showMessage(message) {
    const messageContainer = document.getElementById('message-container');
    if (messageContainer) {
        messageContainer.textContent = message;
        messageContainer.style.display = 'block';
        setTimeout(() => {
            messageContainer.style.display = 'none';
        }, 3000); // Hide the message after 3 seconds
    }
}

function openModal(image) {
    const modal = document.getElementById("imageModal");
    const modalImage = document.getElementById("modalImage");
    const captionText = document.getElementById("caption");

    if (modal && modalImage && captionText) {
        modal.style.display = "block";
        modalImage.src = image.src;
        captionText.textContent = image.alt;
    }
}

function closeModal() {
    const modal = document.getElementById("imageModal");
    if (modal) {
        modal.style.display = "none";
    }
}
