package com.skytask.service;

import com.skytask.channel.ProductSource;
import com.skytask.common.Product;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqService {

    private ProductSource productSource;

    public RabbitmqService(ProductSource productSource) {
        this.productSource = productSource;
    }

    public void getProductListRabbit() {
        productSource.getProductList().send(MessageBuilder.withPayload("getProductsList").build());
    }

    public void createProductRabbit(Product product) {
        productSource.createProduct().send(MessageBuilder.withPayload(product).build());
    }
}
