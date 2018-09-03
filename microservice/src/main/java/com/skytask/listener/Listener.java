package com.skytask.listener;

import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import com.skytask.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
import java.util.List;

public class Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    private ProductService productService;

    public Listener(ProductService productService){
        this.productService = productService;
    }

    @RabbitHandler
    @RabbitListener(queues = "requestProductListQ")
    public String getProductList(@Payload String request) throws IOException {
        LOGGER.info("I received {}", request);
        List<Product> products = productService.getProducts();
        return ProductMapper.list2Json(products);
    }

    @RabbitHandler
    @RabbitListener(queues = "createProductQ")
    public String createProduct(@Payload Product product) {
        LOGGER.info("I received {}", product);
        productService.create(product);
        return "productCreated";
    }

}
