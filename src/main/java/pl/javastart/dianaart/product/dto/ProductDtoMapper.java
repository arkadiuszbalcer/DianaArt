package pl.javastart.dianaart.product.dto;

import pl.javastart.dianaart.product.Product;

public class ProductDtoMapper {
    public static ProductDto map(Product product){
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDetails(),
                product.getCategory(),
                product.getQuantity()
        );
    }
}
