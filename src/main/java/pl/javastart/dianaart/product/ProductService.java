package pl.javastart.dianaart.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.javastart.dianaart.product.dto.ProductDto;
import pl.javastart.dianaart.product.dto.ProductDtoMapper;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductPageResult findAllProducts(int page) {
        int pageSize = 24;
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by("price").ascending());

        Page<Product> productPage = productRepository.findAll(pageRequest);

        List<ProductDto> productDtos = productPage.getContent().stream()
                .map(ProductDtoMapper::map)
                .toList();

        return new ProductPageResult(productDtos, productPage.getTotalPages(), productPage.getNumber());
    }

    public Optional<Product> findProductById(long id) {
        return productRepository.findById(id);
    }

    public List<ProductDto> findProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
        return products.stream()
                .map(ProductDtoMapper::map)
                .sorted(Comparator.comparing(ProductDto::getPrice))
                .toList();
    }

    public List<ProductDto> searchProductsByCategoryAndQuery(String category, String query) {
        List<Product> products = productRepository.searchByCategoryAndQuery(category, query);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found for the given category and query");
        }
        return products.stream().map(ProductDtoMapper::map).toList();
    }


    public List<ProductDto> searchProducts(String query) {
        List<Product> products = productRepository.searchByNameOrDetails(query);
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No products found");
        }
        return products.stream().map(ProductDtoMapper::map).toList();
    }
}
