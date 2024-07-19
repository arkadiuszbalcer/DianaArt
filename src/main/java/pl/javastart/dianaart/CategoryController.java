package pl.javastart.dianaart;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import pl.javastart.dianaart.product.Product;
import pl.javastart.dianaart.product.ProductService;
import pl.javastart.dianaart.product.dto.ProductDto;

import java.util.List;

@Controller
public class CategoryController {
    private ProductService productService;

    public CategoryController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/category")
    public String getCategoryProducts(@RequestParam(defaultValue = "defaultCategory") String category, Model model) {
        List<ProductDto> products = productService.findProductsByCategory(category);
        model.addAttribute("products", products);
        model.addAttribute("category", category);
        return "category";
    }
    }


