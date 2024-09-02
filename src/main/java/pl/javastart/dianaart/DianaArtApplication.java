package pl.javastart.dianaart;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class DianaArtApplication {

 public static void main(String[] args) {
  ConfigurableApplicationContext context = SpringApplication.run(DianaArtApplication.class, args);
 }
}