package com.skytask.service;

import com.skytask.channel.ProductSource;
import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProductServiceManagement {

    private ProductSource productSource;
    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    public ProductServiceManagement(ProductSource productSource, RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
        this.productSource = productSource;
        this.rabbitTemplate = rabbitTemplate;
        this.directExchange = directExchange;
    }

    public List<Product> getProductListRabbit() throws IOException {
        String productList = (String) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "requestProductListKey",
                "getProductList");
        return ProductMapper.json2List(productList, Product.class);
    }

    public void createProductRabbit(Product product) {
        rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "createProductKey",
                product);
    }
}
