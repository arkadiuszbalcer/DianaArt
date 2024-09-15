package pl.javastart.dianaart.product;

import pl.javastart.dianaart.product.dto.ProductDto;

import java.util.List;

public class ProductPageResult {
    private final List<ProductDto> products;
    private final int totalPages;
    private final int currentPage;

    public ProductPageResult(List<ProductDto> products, int totalPages, int currentPage) {
        this.products = products;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
    }

    // Getters

    public List<ProductDto> getProducts() {
        return products;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}

