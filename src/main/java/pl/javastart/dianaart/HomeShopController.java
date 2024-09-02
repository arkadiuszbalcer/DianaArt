package pl.javastart.dianaart;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.dianaart.client.ShoppingCartService;
import pl.javastart.dianaart.product.ProductService;
import pl.javastart.dianaart.product.dto.ProductDto;

import java.util.List;

@Controller
public class HomeShopController {
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;

    public HomeShopController(ProductService productService, ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = productService.findAllProducts();
        int cartCount = shoppingCartService.getShoppingCart().getProducts().size();
        model.addAttribute("price", "Cena produktu");
        model.addAttribute("name", "Nazwa produktu");
        model.addAttribute("details", "Szczegóły");
        model.addAttribute("products", products);
        model.addAttribute("cartCount", cartCount);
        return "homeShop";
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchProducts(@RequestParam String query) {
        List<ProductDto> products = productService.searchProducts(query);
        return ResponseEntity.ok(products);
    }
}
