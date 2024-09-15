package pl.javastart.dianaart.client;
import jakarta.persistence.*;
import pl.javastart.dianaart.product.Product;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class ClientOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_date")
    OffsetDateTime orderDate;

    private String userDetails;
    private Integer quantity;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_order_products",
            joinColumns = @JoinColumn(name = "client_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "client_order_products", joinColumns = @JoinColumn(name = "client_order_id"))
    @MapKeyJoinColumn(name = "product_id")
    @Column(name = "quantity")
    private Map<Product, Integer> productQuantities = new HashMap<>();

    public ClientOrder() {
    }

    public ClientOrder(OffsetDateTime orderDate, String userDetails) {
        this.orderDate = orderDate;
        this.userDetails = userDetails;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public OffsetDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(OffsetDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product, int quantity, OffsetDateTime orderDate) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        productQuantities.merge(product, quantity, Integer::sum);
    }

    public void removeProduct(Product product) {
        productQuantities.remove(product);
    }

    @Override
    public String toString() {
        return "ClientOrder{" +
                "id=" + id +
                ", orderDate='" + orderDate + '\'' +
                ", userDetails='" + userDetails + '\'' +
                ", productQuantities=" + productQuantities +
                '}';
    }

    public List<Product> getProducts() {
        return new ArrayList<>(productQuantities.keySet());
    }

    public void updateProductQuantity(Product product, int quantity) {
        if (quantity > 0) {
            productQuantities.put(product, quantity);
        } else {
            removeProduct(product);
        }
    }
}


