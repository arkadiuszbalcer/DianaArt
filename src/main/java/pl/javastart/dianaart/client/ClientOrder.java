package pl.javastart.dianaart.client;
import jakarta.persistence.*;
import org.springframework.security.core.parameters.P;
import pl.javastart.dianaart.product.Product;
import pl.javastart.dianaart.user.User;

import java.util.ArrayList;
import java.util.List;


import java.util.ArrayList;
import java.util.List;

@Entity
public class ClientOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderDetails;
    private String userDetails;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "client_order_products",
            joinColumns = @JoinColumn(name = "client_order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products = new ArrayList<>();

    /*@ManyToOne(fetch = FetchType.EAGER)

    @JoinColumn(name = "user_roles_id")
    private User user;*/
    public ClientOrder() { }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public ClientOrder(String orderDetails, String userDetails) {
        this.orderDetails = orderDetails;
        this.userDetails = userDetails;
    }
    /*public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(String orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void addProduct(Product product) {
        products.add(product);
        product.addOrder(this);
    }

    @Override
    public String toString() {
        return "ClientOrder{" +
                "id=" + id +
                ", products=" + products +
                ", orderDetails='" + orderDetails + '\'' +
                '}';
    }
}