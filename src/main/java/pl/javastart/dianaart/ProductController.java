package pl.javastart.dianaart;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;
import pl.javastart.dianaart.product.Product;
import pl.javastart.dianaart.product.ProductService;
import pl.javastart.dianaart.product.dto.ProductDto;

import java.util.List;
import java.util.Optional;
@Controller
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/product/{id}")
    public String getMovie(@PathVariable long id,
                           Model model) {
        ProductDto product = productService.findProductById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        model.addAttribute("product", product);
        return "product";
    }
}

