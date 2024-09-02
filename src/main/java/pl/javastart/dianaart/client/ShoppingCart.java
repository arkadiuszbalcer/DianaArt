package pl.javastart.dianaart.client;
import pl.javastart.dianaart.product.Product;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private Map<Product, Integer> products = new HashMap<>();

    public Map<Product, Integer> getProducts() {
        return products;
    }

    // Dodaje produkt do koszyka lub aktualizuje jego ilość, jeśli już istnieje
    public void addProduct(Product product, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        products.merge(product, quantity, Integer::sum);
    }

    // Aktualizuje ilość produktu w koszyku
    public void updateProductQuantity(Product product, int quantity) {
        if (quantity > 0) {
            products.put(product, quantity);
        } else {
            removeProduct(product.getId());
        }
    }

    // Usuwa produkt z koszyka
    public void removeProduct(Long productId) {
        products.entrySet().removeIf(entry -> entry.getKey().getId().equals(productId));
    }

    // Oblicza łączną wartość koszyka
    public double getTotal() {
        return products.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
                .sum();
    }

    // Czyści koszyk
    public void clear() {
        products.clear();
    }
}
