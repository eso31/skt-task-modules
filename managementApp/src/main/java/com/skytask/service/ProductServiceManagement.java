package com.skytask.service;

import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ProductServiceManagement {

    private RabbitTemplate rabbitTemplate;
    private DirectExchange directExchange;

    public ProductServiceManagement(RabbitTemplate rabbitTemplate, DirectExchange directExchange) {
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

    public String createProductRabbit(Product product) throws IllegalArgumentException{
        if(product==null)
            throw new IllegalArgumentException();

        return (String) rabbitTemplate.convertSendAndReceive(
                directExchange.getName(),
                "createProductKey",
                product);
    }
}
