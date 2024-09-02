package pl.javastart.dianaart;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.javastart.dianaart.client.ShoppingCartService;
import pl.javastart.dianaart.product.ProductService;
import pl.javastart.dianaart.product.dto.ProductDto;
import java.util.List;


@Controller
public class CategoryController {
    private final ProductService productService;
    private final ShoppingCartService shoppingCartService;

    public CategoryController(ProductService productService, ShoppingCartService shoppingCartService) {
        this.productService = productService;
        this.shoppingCartService = shoppingCartService;
    }

    @GetMapping("/category")
    public String getCategoryProducts(@RequestParam(defaultValue = "defaultCategory") String category, Model model) {
        List<ProductDto> products = productService.findProductsByCategory(category);
        int cartCount = shoppingCartService.getShoppingCart().getProducts().size();
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("cartCount", cartCount);
        return "category";
    }

    @GetMapping("/category-search")
    public String searchProductsByCategory(@RequestParam String category, @RequestParam String query, Model model) {
        List<ProductDto> products = productService.searchProductsByCategoryAndQuery(category, query);
        int cartCount = shoppingCartService.getShoppingCart().getProducts().size();
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        model.addAttribute("cartCount", cartCount);
        return "category";
    }
}

