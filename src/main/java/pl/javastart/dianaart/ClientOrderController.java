package pl.javastart.dianaart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.javastart.dianaart.client.*;
import pl.javastart.dianaart.product.ProductService;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;


@Controller
@RequestMapping("/shoppingCart")
public class ClientOrderController {

    private final ShoppingCartService shoppingCartService;
    private final ProductService productService;
    private final ClientOrderRepository clientOrderRepository;
    private final ClientOrderService clientOrderService;
    private final ResponseService responseService;

    @Autowired
    public ClientOrderController(ShoppingCartService shoppingCartService, ProductService productService, ClientOrderRepository clientOrderRepository, ClientOrderService clientOrderService, ResponseService responseService) {
        this.shoppingCartService = shoppingCartService;
        this.productService = productService;
        this.clientOrderRepository = clientOrderRepository;
        this.clientOrderService = clientOrderService;
        this.responseService = responseService;
    }

    @GetMapping
    public String getOrder(Model model) {
        Map<String, Object> attributes = shoppingCartService.prepareCartAttributes();
        model.addAllAttributes(attributes);
        return "shoppingCart";
    }


    @PostMapping("/Product")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addProductToOrder(@RequestParam Long productId, @RequestParam Integer quantity, @RequestParam OffsetDateTime orderDate)  {
        return shoppingCartService.addProductToCart(productId, quantity, orderDate);
    }


    @PatchMapping("/Quantity")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateProductQuantity(@RequestParam Long productId, @RequestParam Integer quantity) {
        Map<String, Object> response = shoppingCartService.updateProductQuantity(productId, quantity);
        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/Product")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> removeProductFromOrder(@RequestParam Long productId) {
        Map<String, Object> response = clientOrderService.removeProductFromOrder(productId);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/totalOrderSum")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getTotalOrderSum() {
        Map<String, Object> response = new HashMap<>();
        response.put("totalOrderSum", shoppingCartService.getTotal());
        return ResponseEntity.ok(response);
    }
}