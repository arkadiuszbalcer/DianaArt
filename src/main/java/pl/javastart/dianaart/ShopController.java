package pl.javastart.dianaart;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.javastart.dianaart.product.ProductService;
import pl.javastart.dianaart.product.dto.ProductDto;

import java.util.List;

@Controller
public class ShopController {
    private final ProductService productService;

    public ShopController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<ProductDto> products = productService.FindAllProduct();
        model.addAttribute("price", "Cena produktu");
        model.addAttribute("name", "Nazwa produktu");
        model.addAttribute("details", "Szczegóły");
        model.addAttribute("products", products);
        model.addAttribute("products", products);
        return "Index";
    }
}