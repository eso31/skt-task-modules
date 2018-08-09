package com.skytask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytask.common.Product;
import com.skytask.listener.Listener;
import com.skytask.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MicroserviceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private Listener listener;

    @Test
    public void testListener() throws JsonProcessingException {
        int previousSize = productService.getProducts().size();

        Product product = new Product();
        product.setName("testName");
        product.setDescription("testDescription");
        product.setStock(10);
        product.setPrice(25.5);

        String responseList = listener.getProductList("getProductList");
        String responseCreate = listener.createProduct(product);

        assertNotEquals(responseCreate, responseList);
        assertEquals(previousSize + 1, productService.getProducts().size());
    }
}
