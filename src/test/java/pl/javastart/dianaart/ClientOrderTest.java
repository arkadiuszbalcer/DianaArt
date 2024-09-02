package pl.javastart.dianaart;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.javastart.dianaart.client.ClientOrder;
import pl.javastart.dianaart.product.Product;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class ClientOrderTest {
 private ClientOrder clientOrder;
 private Product product1;
 private Product product2;

 @BeforeEach
void setUp(){
     clientOrder = new ClientOrder("Order Details","User Details");
     product1 = new Product();
     product1.setId(1L);
     product2 = new Product();
     product2.setId(2L);
 }
 @Test
    void testAddProduct(){
     clientOrder.addProduct(product1, 5);
     assertEquals(5, clientOrder.getProductQuantities().get(product1));
 }
 @Test
    void testAddProductWithNegativeQuantity(){
     assertThrows(IllegalArgumentException.class, () -> clientOrder.addProduct(product1,-1));
 }
 @Test
    void testUpdateProductQuantity(){
     clientOrder.addProduct(product1, 5);
     clientOrder.updateProductQuantity(product1, 10);
     assertEquals(10, clientOrder.getProductQuantities().get(product1));
 }
 @Test
    void testRemoveProduct(){
     clientOrder.addProduct(product1, 2);
     clientOrder.removeProduct(product1);
     assertFalse(clientOrder.getProductQuantities().containsKey(product1));
 }
 @Test
  void  TestGetProducts(){
     clientOrder.addProduct(product1, 3);
     clientOrder.addProduct(product2, 3);
     assertTrue(clientOrder.getProducts().contains(product1));
     assertTrue(clientOrder.getProducts().contains(product2));
 }
 @Test
    void testSetProductsQuantities(){
     clientOrder.addProduct(product1, 4);
     Map<Product, Integer> quantities = clientOrder.getProductQuantities();
     quantities.put(product2, 10);
     clientOrder.setProductQuantities(quantities);
     assertEquals(10, clientOrder.getProductQuantities().get(product2));
 }

    @Test
    void testToString() {
        clientOrder.addProduct(product1, 5);
        String expectedString = "ClientOrder{id=null, orderDetails='Order Details', userDetails='User Details', productQuantities={" + product1.toString() + "=5}}";
        assertTrue(clientOrder.toString().contains("ClientOrder"));
    }
}
