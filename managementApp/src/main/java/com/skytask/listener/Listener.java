package com.skytask.listener;

import com.skytask.channel.ProductSource;
import com.skytask.common.Channels;
import com.skytask.common.Product;
import com.skytask.common.ProductMapper;
import com.skytask.store.ProductStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

import java.io.IOException;
import java.util.List;

@EnableBinding(ProductSource.class)
public class Listener {

    private final static Logger LOGGER = LoggerFactory.getLogger(Listener.class);
    private ProductStore productStore;

    public Listener(ProductStore productService) {
        this.productStore = productService;
    }

    @StreamListener(Channels.RESPONSE_PRODUCT_LIST)
    public void updateProductList(String products) throws IOException {
        LOGGER.info("I received: {}", products);
        List<Product> productList = ProductMapper.json2List(products, Product.class);
        LOGGER.info(String.valueOf(productList.size()));
        productList.forEach(product -> LOGGER.info(product.toString()));
        productStore.setProducts(productList);
    }
}
