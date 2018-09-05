package com.skytask;

import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import com.skytask.listener.Listener;
import com.skytask.repository.ProductRepository;
import com.skytask.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MicroserviceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private Listener listener;

    @Test
    public void microserviceTest() throws IOException {
        int previousSize = productService.getProducts().size();

        Product expectedProduct = new Product();
        expectedProduct.setName("testName");
        expectedProduct.setDescription("testDescription");
        expectedProduct.setStock(10);
        expectedProduct.setPrice(25.5);

        String responseList = listener.getProductList("getProductList");
        assertEquals(ProductMapper.json2List(responseList, Product.class).size(), previousSize);

        Long id = listener.createProduct(expectedProduct);
        assertNotNull(id);

        Product product = productRepository.findOne(id);
        assertEquals(expectedProduct.getName(), product.getName());
        assertEquals(expectedProduct.getDescription(), product.getDescription());
        assertEquals(expectedProduct.getStock(), product.getStock());
        assertEquals(expectedProduct.getPrice(), product.getPrice(), 0);

        assertEquals(previousSize + 1, productService.getProducts().size());
    }
}
