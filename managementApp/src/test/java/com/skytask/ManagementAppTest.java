package com.skytask;

import com.skytask.common.Product;
import com.skytask.listener.Listener;
import com.skytask.store.ProductStore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagementAppTest {

    @Autowired
    private ProductStore productStore;

    @Autowired
    private Listener listener;

    @Test
    public void testListener() throws IOException, InterruptedException, ExecutionException {
        String products = "[{\"id\":13,\"name\":\"gansito\",\"description\":\"chocolate\",\"price\":20.0,\"stock\":50},{\"id\":14,\"name\":\"coca\",\"description\":\"refresco\",\"price\":15.0,\"stock\":60}]";
        List<Product> expectedList = new ArrayList<>();
        Product product1 = new Product();
        product1.setId(13L);
        product1.setName("gansito");
        product1.setDescription("chocolate");
        product1.setStock(50);
        product1.setPrice(20);

        Product product2 = new Product();
        product2.setId(14L);
        product2.setName("coca");
        product2.setDescription("refresco");
        product2.setStock(60);
        product2.setPrice(15.0);

        expectedList.add(product1);
        expectedList.add(product2);

        listener.updateProductList(products);

        List<Product> productsList = productStore.getProducts().get();

        assertEquals(expectedList.size(), productsList.size());
        assertEquals(productsList.toString(), expectedList.toString());
    }
}
