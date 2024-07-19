package pl.javastart.dianaart.product;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product>findAll();

    List<Product> findByCategory(String category);
}
