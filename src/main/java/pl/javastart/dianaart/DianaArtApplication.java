package pl.javastart.dianaart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.javastart.dianaart.client.ClientOrder;
import pl.javastart.dianaart.client.ClientOrderRepository;
import pl.javastart.dianaart.product.Product;
import pl.javastart.dianaart.product.ProductRepository;
import pl.javastart.dianaart.user.User;
import pl.javastart.dianaart.user.UserRepository;
import pl.javastart.dianaart.user.UserRoleRepository;

import static pl.javastart.dianaart.client.ClientOrderRepository.*;

@SpringBootApplication
public class DianaArtApplication {

 public static void main(String[] args) {
  ConfigurableApplicationContext context = SpringApplication.run(DianaArtApplication.class, args);

 }
}
