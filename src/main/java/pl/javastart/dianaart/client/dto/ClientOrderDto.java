package pl.javastart.dianaart.client.dto;
import pl.javastart.dianaart.product.Product;
import java.util.List;

public class ClientOrderDto {
    private Long id;
    private String orderDetails;
    private String userDetails;
    private List<Product> products;

    public ClientOrderDto(Long id, String orderDetails, List<Product> products, String userDetails) {
        this.id = id;
        this.orderDetails = orderDetails;
        this.userDetails = userDetails;
        this.products = products;
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

    @Override
    public String toString() {
        return "ClientOrderDto{" +
                "id=" + id +
                ", orderDetails='" + orderDetails + '\'' +
                ", userDetails='" + userDetails + '\'' +
                ", products=" + products +
                '}';
    }
}
