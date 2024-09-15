/*package pl.javastart.dianaart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.javastart.dianaart.client.ShoppingCart;
import pl.javastart.dianaart.product.Product;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class ShoppingCartTest {
    public ShoppingCart shoppingCart;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart();
        product1 = new Product();
        product1.setId(1L);
        product1.setName("Product1");
        product1.setPrice(10.0);

        product2 = new Product();
        product2.setId(2l);
        product2.setName("Product2");
        product2.setPrice(20.0);
    }

    @Test
    void testAddProduct() {
        shoppingCart.addProduct(product1, 10, orderDate);
        assertEquals(10, shoppingCart.getProducts().get(product1));

    }

    @Test
    void testAddProductWithNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> shoppingCart.addProduct(product1, -10, orderDate));
    }

    @Test
    void testUpdateProductQuantity() {
        shoppingCart.addProduct(product1, 2, orderDate);
        shoppingCart.updateProductQuantity(product1, 5);
        assertEquals(5, shoppingCart.getProducts().get(product1));
    }

    @Test
    void testUpdateProductQuantiryToZeroRemovesProduct() {
        shoppingCart.addProduct(product1, 2, orderDate);
        shoppingCart.updateProductQuantity(product1, 0);
        assertFalse(shoppingCart.getProducts().containsKey(product1));
    }

    @Test
    void testRemoveProduct() {
        shoppingCart.addProduct(product1, 2, orderDate);
        shoppingCart.removeProduct(1L);
        assertFalse(shoppingCart.getProducts().containsKey(product1));
    }

    @Test
    void testGetTotal() {
        shoppingCart.addProduct(product1, 2, orderDate); // 2 * 10.0 = 20.0
        shoppingCart.addProduct(product2, 1, orderDate); // 1 * 20.0 = 20.0
        assertEquals(40.0, shoppingCart.getTotal());
    }
    @Test
    void testClear() {
        shoppingCart.addProduct(product1, 2, orderDate);
        shoppingCart.clear();
        assertTrue(shoppingCart.getProducts().isEmpty());
    }
    @Test
    void testAddMultipleProducts() {
        shoppingCart.addProduct(product1, 1, orderDate);
        shoppingCart.addProduct(product2, 3, orderDate);
        Map<Product, Integer> products = shoppingCart.getProducts();
        assertEquals(1, products.get(product1));
        assertEquals(3, products.get(product2));
    }
}

*/