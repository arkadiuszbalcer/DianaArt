package pl.javastart.dianaart;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import pl.javastart.dianaart.client.ShoppingCartService;
import pl.javastart.dianaart.product.Product;
import pl.javastart.dianaart.product.ProductService;

@Controller
public class ProductController {
    private final ProductService productService;
    private ShoppingCartService shoppingCartService;

    public ProductController(ProductService productService, ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable long id,
                             Model model) {
        Product product = productService.findProductById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("product", product);
        return "product";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/cart/count")
    @ResponseBody
    public ResponseEntity<Integer> getCartCount() {
        int cartCount = shoppingCartService.getShoppingCart().getProducts().size();
        return ResponseEntity.ok(cartCount);
    }
}