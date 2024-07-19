package pl.javastart.dianaart.client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.javastart.dianaart.product.Product;

import java.util.Optional;

@Service
public class ClientService {
    private final ClientOrderRepository clientOrderRepository;

    @Autowired
    public ClientService(ClientOrderRepository clientOrderRepository) {
        this.clientOrderRepository = clientOrderRepository;
    }

    public ClientOrder createOrder(ClientOrder order) {
        return clientOrderRepository.save(order);
    }

    public Optional<ClientOrder> getOrder(Long id) {
        return clientOrderRepository.findById(id);
    }

    public Iterable<ClientOrder> getAllOrders() {
        return clientOrderRepository.findAll();
    }

    public void deleteOrder(Long id) {
        clientOrderRepository.deleteById(id);
    }

    public ClientOrder addProductToOrder(Long orderId, Product product) {
        Optional<ClientOrder> orderOpt = clientOrderRepository.findById(orderId);
        if (orderOpt.isPresent()) {
            ClientOrder order = orderOpt.get();
            order.addProduct(product);
            return clientOrderRepository.save(order);
        }
        return null;
    }
}

