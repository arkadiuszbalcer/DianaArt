document.addEventListener('DOMContentLoaded', () => {
    updateOrder();
    updateButtonStates();
    updateTotalOrderSum();
    initializeProductSearch(); // Inicjalizacja funkcji wyszukiwania

    // Add product to cart
    const addToCartForms = document.querySelectorAll('.add-to-cart-form');
    addToCartForms.forEach(form => {
        form.addEventListener('submit', function(event) {
            event.preventDefault();
            const formData = new FormData(form);
            const productId = formData.get('productId');
            const quantity = formData.get('quantity');
            const csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

            fetch('/shoppingCart/Product', {
                method: 'POST',
                headers: {
                    'X-CSRF-TOKEN': csrfToken,
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: new URLSearchParams({
                    productId: String(productId), // Ensure it's a string
                    quantity: String(quantity)    // Ensure it's a string
                })
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Add to cart response:', data); // Log response data
                    if (data.success) {
                        updateCartCount(data.cartCount); // Update cart count on success
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

    function updateCartCount(count) {
        const cartCountElement = document.getElementById('cart-count');
        if (cartCountElement) {
            cartCountElement.textContent = count;
        } else {
            console.error('Element #cart-count not found');
        }
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
});

function initializeProductSearch() {
    const searchInput = document.getElementById('searchInput');
    if (!searchInput) return; // Exit if the search input element is not found

    const productList = document.getElementById('productList');
    if (!productList) return; // Exit if the product list element is not found

    const products = productList.querySelectorAll('.product');
    if (products.length === 0) return; // Exit if no products are found

    searchInput.addEventListener('input', function () {
        const query = searchInput.value.toLowerCase();

        products.forEach(product => {
            const productName = product.querySelector('h3') ? product.querySelector('h3').textContent.toLowerCase() : '';
            const productPrice = product.querySelector('p') ? product.querySelector('p').textContent.toLowerCase() : '';

            if (productName.includes(query) || productPrice.includes(query)) {
                product.style.display = '';
            } else {
                product.style.display = 'none';
            }
        });
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
