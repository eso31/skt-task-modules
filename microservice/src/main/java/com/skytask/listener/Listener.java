package com.skytask.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.skytask.common.Channels;
import com.skytask.channel.ProductSource;
import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import com.skytask.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;

import java.util.List;

@EnableBinding(ProductSource.class)
public class Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private ProductService productService;

    public Listener(ProductService productService) {
        this.productService = productService;
    }

    @StreamListener(Channels.CREATE_PRODUCT)
    @SendTo(Channels.RESPONSE_PRODUCT_LIST)
    public String createProduct(Product product) throws JsonProcessingException {
        LOGGER.info("I received: {}", product);
        productService.create(product);
        List<Product> products = productService.getProducts();
        return new ProductMapper().productList2Json(products);
    }

    @StreamListener(Channels.REQUEST_PRODUCT_LIST)
    @SendTo(Channels.RESPONSE_PRODUCT_LIST)
    public String getProductList(String message) throws JsonProcessingException {
        LOGGER.info("I received {}", message);
        List<Product> products = productService.getProducts();
        return new ProductMapper().productList2Json(products);
    }
}
