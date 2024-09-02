package pl.javastart.dianaart.client;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.javastart.dianaart.product.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.javastart.dianaart.product.ProductService;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private static final Logger logger = LoggerFactory.getLogger(ShoppingCartService.class);
    private final ShoppingCart shoppingCart = new ShoppingCart();
    private final ClientOrderRepository clientOrderRepository;
    private final ProductService productService;
    private final ResponseService responseService;
    private ClientOrder clientOrder = new ClientOrder();

    @Lazy
    @Autowired
    private ClientOrderService clientOrderService;

    @Autowired
    public ShoppingCartService(ClientOrderRepository clientOrderRepository, ProductService productService,
                               ResponseService responseService) {
        this.clientOrderRepository = clientOrderRepository;
        this.productService = productService;
        this.responseService = responseService;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void addProduct(Product product, int quantity) {
        shoppingCart.addProduct(product, quantity);
    }


    public void removeProduct(Long productId) {
        logger.debug("Attempting to remove product with ID: {}", productId);

        shoppingCart.removeProduct(productId);

        if (clientOrder != null) {
            Product productToRemove = clientOrder.getProducts().stream()
                    .filter(product -> product.getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (productToRemove != null) {
                clientOrder.removeProduct(productToRemove);
                logger.debug("Removed product from ClientOrder: {}", productToRemove);
            } else {
                logger.debug("Product with ID {} not found in ClientOrder.", productId);
            }
        } else {
            logger.warn("ClientOrder is null. Product not removed.");
        }
    }

    public void updateProductQuantity(Product product, int quantity) {
        shoppingCart.updateProductQuantity(product, quantity);
        if (clientOrder != null) {
            clientOrder.updateProductQuantity(product, quantity);
        } else {
            logger.warn("ClientOrder is null. Product quantity not updated.");
        }
    }

    public double getTotal() {
        return shoppingCart.getTotal();
    }

    public ClientOrder getClientOrder() {
        return clientOrder;
    }

    public void setClientOrder(ClientOrder clientOrder) {
        this.clientOrder = clientOrder;
    }

    // Nowa metoda obsługująca dodawanie produktu i tworzenie odpowiedzi
    public ResponseEntity<Map<String, Object>> addProductToCart(Long productId, Integer quantity) {
        Optional<Product> productOpt = productService.findProductById(productId);

        if (productOpt.isPresent() && quantity != null && quantity > 0) {
            Product product = productOpt.get();
            addProduct(product, quantity);
            clientOrderService.addProduct(product, quantity);
            return ResponseEntity.ok(responseService.createSuccessResponse(getShoppingCart().getProducts().size()));
        } else {
            return ResponseEntity.ok(responseService.createErrorResponse("Product not found or invalid quantity"));
        }
    }

    public Map<String, Object> prepareCartAttributes() {
        Map<Product, Integer> productQuantities = shoppingCart.getProducts();
        double totalOrderSum = getTotal();

        Map<String, Object> attributes = new HashMap<>();
        if (productQuantities.isEmpty()) {
            attributes.put("emptyCartMessage", "Koszyk jest pusty, dodaj produkty do koszyka.");
        } else {
            attributes.put("productQuantities", productQuantities);
            attributes.put("totalOrderSum", totalOrderSum);
        }

        return attributes;
    }

    @Transactional
    public Map<String, Object> updateProductQuantity(Long productId, Integer quantity) {
        Map<String, Object> response = new HashMap<>();
        Optional<Product> productOpt = productService.findProductById(productId);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            if (quantity > 0) {
                updateProductQuantity(product, quantity);
            } else {
                removeProduct(productId);
            }
            clientOrderRepository.save(clientOrder);
            response.put("success", true);
            response.put("totalOrderSum", getTotal());
        } else {
            response.put("success", false);
            response.put("error", "Product not found");
        }

        return response;
    }
        }
