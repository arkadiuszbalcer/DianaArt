package pl.javastart.dianaart;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.javastart.dianaart.client.ClientOrder;
import pl.javastart.dianaart.client.ClientOrderRepository;
import pl.javastart.dianaart.client.ClientService;
import pl.javastart.dianaart.product.Product;

import java.util.Optional;

@RestController
@RequestMapping("/clientOrder")
public class ClientOrderController {
    private final ClientService clientService;

    @Autowired
    public ClientOrderController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<ClientOrder> createOrder(@RequestBody ClientOrder order) {
        return ResponseEntity.ok(clientService.createOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientOrder> getOrder(@PathVariable Long id) {
        Optional<ClientOrder> order = clientService.getOrder(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Iterable<ClientOrder>> getAllOrders() {
        return ResponseEntity.ok(clientService.getAllOrders());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        clientService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{orderId}/products")
    public ResponseEntity<ClientOrder> addProductToOrder(@PathVariable Long orderId, @RequestBody Product product) {
        ClientOrder updatedOrder = clientService.addProductToOrder(orderId, product);
        return updatedOrder != null ? ResponseEntity.ok(updatedOrder) : ResponseEntity.notFound().build();
    }
}

