package pl.javastart.dianaart.client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.javastart.dianaart.product.Product;
import java.util.HashMap;
import java.util.Map;

@Service
public class ClientOrderService {
    private final ClientOrderRepository clientOrderRepository;
    private final ShoppingCartService shoppingCartService;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);


    public ClientOrderService(ClientOrderRepository clientOrderRepository, ShoppingCartService shoppingCartService) {
        this.clientOrderRepository = clientOrderRepository;
        this.shoppingCartService = shoppingCartService;
    }

    public void addProduct(Product product, int quantity) {
        ClientOrder clientOrder = shoppingCartService.getClientOrder();

        if (clientOrder == null) {
            clientOrder = new ClientOrder();
            shoppingCartService.setClientOrder(clientOrder);

        }
        clientOrder.addProduct(product, quantity);
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