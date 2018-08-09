package com.skytask.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytask.common.Product;
import com.skytask.service.ProductService;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(JUnit4ClassRunner.class)
public class ListenerTest {

    private ProductService productService;

    private Listener listener;

    private Product product;

    @Before
    public void setUp() {
        productService = EasyMock.createMock(ProductService.class);
        listener = new Listener(productService);
        product = new Product();
        product.setName("testName");
        product.setDescription("testDescription");
        product.setStock(10);
        product.setPrice(25.5);
    }

    @Test
    public void testCreateProduct() throws JsonProcessingException {

        String expected = "[{\"id\":null,\"name\":\"testName\",\"description\":\"testDescription\",\"price\":25.5,\"stock\":10}]";

        productService.create(product);
        EasyMock.expectLastCall();
        EasyMock.expect(productService.getProducts()).andReturn(Arrays.asList(product));
        EasyMock.replay(productService);

        String response = listener.createProduct(product);
        assertEquals(expected, response);

        EasyMock.verify(productService);
    }

    @Test
    public void testGetProductList() throws JsonProcessingException {

        String expected = "[{\"id\":null,\"name\":\"testName\",\"description\":\"testDescription\",\"price\":25.5,\"stock\":10}]";

        EasyMock.expect(productService.getProducts()).andReturn(Arrays.asList(product));
        EasyMock.replay(productService);

        String response = listener.getProductList("getProductList");
        assertEquals(expected, response);

        EasyMock.verify(productService);
    }
}