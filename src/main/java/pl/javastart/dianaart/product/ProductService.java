package pl.javastart.dianaart.product;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import pl.javastart.dianaart.product.dto.ProductDto;
import pl.javastart.dianaart.product.dto.ProductDtoMapper;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDto> FindAllProduct() {
        return productRepository.findAll().stream()
                .map(ProductDtoMapper::map)
                .toList();
    }

    public Optional<ProductDto> findProductById(long id) {
        return productRepository.findById(id).map(ProductDtoMapper::map);
    }

    public List<ProductDto> findProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return products.stream().map(ProductDtoMapper::map).toList();
    }
}


