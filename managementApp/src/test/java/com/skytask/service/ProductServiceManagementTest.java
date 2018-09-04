package com.skytask.service;

import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4ClassRunner.class)
public class ProductServiceManagementTest {

    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;
    private ProductServiceManagement productServiceManagement;

    @Before
    public void setUp(){
        rabbitTemplate = EasyMock.createMock(RabbitTemplate.class);
        directExchange = EasyMock.createMock(DirectExchange.class);
        productServiceManagement = new ProductServiceManagement(rabbitTemplate, directExchange);
    }

    @Test
    public void getProductListRabbitEmptyTest() throws IOException {
        EasyMock.expect(directExchange.getName()).andReturn("productExchange");
        EasyMock.expect(rabbitTemplate.convertSendAndReceive("productExchange", "requestProductListKey", "getProductList")).andReturn("[]");
        EasyMock.replay(directExchange);
        EasyMock.replay(rabbitTemplate);

        List<Product> products = productServiceManagement.getProductListRabbit();
        assertEquals(new ArrayList<Product>(), products);

        EasyMock.verify(directExchange);
        EasyMock.verify(rabbitTemplate);
    }

    @Test
    public void getProductListRabbitNotEmptyTest() throws IOException {
        Product product = new Product();
        product.setId(1L);
        product.setName("Coca");
        product.setPrice(20);
        product.setDescription("Refresco de 2 Litros");
        product.setStock(25);

        List<Product> expectedList = Arrays.asList(product);

        EasyMock.expect(directExchange.getName()).andReturn("productExchange");
        EasyMock.expect(rabbitTemplate.convertSendAndReceive("productExchange", "requestProductListKey", "getProductList")).andReturn(ProductMapper.list2Json(expectedList));
        EasyMock.replay(directExchange);
        EasyMock.replay(rabbitTemplate);

        List<Product> products = productServiceManagement.getProductListRabbit();
        assertEquals(expectedList.toString(), products.toString());

        EasyMock.verify(directExchange);
        EasyMock.verify(rabbitTemplate);
    }

    @Test
    public void createProductRabbitTest(){
        Product product = new Product();
        product.setId(1L);
        product.setName("Coca");
        product.setPrice(20);
        product.setDescription("Refresco de 2 Litros");
        product.setStock(25);

        EasyMock.expect(directExchange.getName()).andReturn("productExchange");
        EasyMock.expect(rabbitTemplate.convertSendAndReceive("productExchange", "createProductKey", product)).andReturn("productCreated");
        EasyMock.replay(directExchange);
        EasyMock.replay(rabbitTemplate);

        String answer = productServiceManagement.createProductRabbit(product);
        assertEquals("productCreated", answer);

        EasyMock.verify(directExchange);
        EasyMock.verify(rabbitTemplate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createProductRabbitErrorTest(){
        Product product = null;

        EasyMock.expect(directExchange.getName()).andReturn("productExchange");
        EasyMock.expect(rabbitTemplate.convertSendAndReceive("productExchange", "createProductKey", product)).andReturn("productCreated");
        EasyMock.replay(directExchange);
        EasyMock.replay(rabbitTemplate);

        String answer = productServiceManagement.createProductRabbit(product);
        assertEquals("productCreated", answer);

        EasyMock.verify(directExchange);
        EasyMock.verify(rabbitTemplate);
    }

}