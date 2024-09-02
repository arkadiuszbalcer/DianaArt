package pl.javastart.dianaart.product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Override
    List<Product> findAll();

    // Wyszukiwanie produktów według kategorii
    List<Product> findByCategory(String categoryName);

    // Wyszukiwanie produktów według nazwy lub szczegółów
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.details) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Product> searchByNameOrDetails(@Param("query") String query);

    // Wyszukiwanie produktów według kategorii oraz wyszukiwanie tekstowe
    @Query("SELECT p FROM Product p WHERE LOWER(p.category) = LOWER(:category) AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(p.details) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<Product> searchByCategoryAndQuery(@Param("category") String category, @Param("query") String query);
}
