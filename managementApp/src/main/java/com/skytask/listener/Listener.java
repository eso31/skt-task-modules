package com.skytask.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.skytask.channel.ProductSource;
import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import com.skytask.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.io.IOException;
import java.util.List;

@EnableBinding(ProductSource.class)
public class Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private ProductService productService;

    public Listener(ProductService productService) {
        this.productService = productService;
    }

    @StreamListener("responseProductListChannel")
    public void updateProductList(String products) throws IOException {
        LOGGER.info("I received: {}", products);
        List<Product> productList = new ProductMapper().json2ProductList(products);
        LOGGER.info(String.valueOf(productList.size()));
        productList.forEach(product -> LOGGER.info(product.toString()));
        productService.setProducts(productList);
    }
}
