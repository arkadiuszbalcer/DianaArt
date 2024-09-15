package pl.javastart.dianaart.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.javastart.dianaart.config.security.CustomUserDetailsService;
import pl.javastart.dianaart.product.Product;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClientOrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final ShoppingCartService shoppingCartService;
    private final CustomUserDetailsService userDetailsService;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);


    public ClientOrderService(ClientOrderRepository clientOrderRepository, ShoppingCartService shoppingCartService, CustomUserDetailsService userDetailsService) {
        this.clientOrderRepository = clientOrderRepository;
        this.shoppingCartService = shoppingCartService;
        this.userDetailsService = userDetailsService;
    }

    public void addProduct(Product product, int quantity, OffsetDateTime orderDate) {
        ClientOrder clientOrder = shoppingCartService.getClientOrder();

        if (clientOrder == null) {
            clientOrder = new ClientOrder();
            shoppingCartService.setClientOrder(clientOrder);

        }
        if (clientOrder.getOrderDate() == null) {
            clientOrder.setOrderDate(orderDate != null ? orderDate : OffsetDateTime.now());
            logger.info("Order date set to: {}", clientOrder.getOrderDate());
        }
        String userEmail = userDetailsService.getCurrentUserEmail(); // Wywołanie wcześniej zdefiniowanej metody
        if (userEmail != null && clientOrder.getUserDetails() == null) {
            clientOrder.setUserDetails(userEmail);
            logger.info("User details set to: {}", userEmail);
        }
        clientOrder.addProduct(product, quantity, orderDate);
        clientOrderRepository.save(clientOrder);
    }

    public Map<String, Object> removeProductFromOrder(Long productId) {
        Map<String, Object> response = new HashMap<>();
        ClientOrder clientOrder = shoppingCartService.getClientOrder();  // Pobierz aktualne zamówienie klienta

        if (clientOrder == null) {
            response.put("success", false);
            response.put("error", "Order not found");
            return response;
        }

        // Znajdź i usuń produkt z zamówienia
        boolean removed = clientOrder.getProducts().removeIf(product -> product.getId().equals(productId));

        if (removed) {
            // Usuń produkt również z koszyka
            shoppingCartService.removeProduct(productId);

            if (clientOrder.getProducts().isEmpty()) {
                // Jeśli zamówienie jest teraz puste, usuń je z bazy danych
                clientOrderRepository.delete(clientOrder);
                shoppingCartService.setClientOrder(null);  // Zresetuj zamówienie w koszyku
            } else {
                // W przeciwnym razie, zapisz zaktualizowane zamówienie
                clientOrderRepository.save(clientOrder);
            }

            response.put("success", true);
            response.put("cartCount", clientOrder.getProducts().size());  // Zaktualizuj z liczbą produktów w koszyku
        } else {
            response.put("success", false);
            response.put("error", "Product not found in the order");
        }

        return response;
    }
}