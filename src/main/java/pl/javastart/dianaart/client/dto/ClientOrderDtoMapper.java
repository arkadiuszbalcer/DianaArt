package pl.javastart.dianaart.client.dto;
import pl.javastart.dianaart.product.Product;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClientOrderDtoMapper {
    private Long id;
    private String orderDetails;
    private List<Product> products;
    private String userDetails;
    private Map<Product, Integer> productQuantities;

    public ClientOrderDtoMapper(Long id, String orderDetails, List<Product> products, String userDetails, Map<Product, Integer> productQuantities) {
        this.id = id;
        this.orderDetails = orderDetails;
        this.products = products;
        this.userDetails = userDetails;
        this.productQuantities = productQuantities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientOrderDtoMapper that = (ClientOrderDtoMapper) o;
        return Objects.equals(id, that.id) && Objects.equals(orderDetails, that.orderDetails) && Objects.equals(products, that.products) && Objects.equals(userDetails, that.userDetails) && Objects.equals(productQuantities, that.productQuantities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDetails, products, userDetails, productQuantities);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public Map<Product, Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(Map<Product, Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }
}
