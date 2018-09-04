package com.skytask.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("rabbitmq.config")
public class RabbitMQVariables {
    public static class Queues {
        private String productList;
        private String createProduct;

        public String getProductList() {
            return productList;
        }

        public void setProductList(String productList) {
            this.productList = productList;
        }

        public String getCreateProduct() {
            return createProduct;
        }

        public void setCreateProduct(String createProduct) {
            this.createProduct = createProduct;
        }
    }

    public static class RoutingKeys {
        private String productList;
        private String createProduct;

        public String getProductList() {
            return productList;
        }

        public void setProductList(String productList) {
            this.productList = productList;
        }

        public String getCreateProduct() {
            return createProduct;
        }

        public void setCreateProduct(String createProduct) {
            this.createProduct = createProduct;
        }
    }


    private Queues queues;
    private String exchange;
    private RoutingKeys routingKeys;


    public Queues getQueues() {
        return queues;
    }

    public void setQueues(Queues queues) {
        this.queues = queues;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public RoutingKeys getRoutingKeys() {
        return routingKeys;
    }

    public void setRoutingKeys(RoutingKeys routingKeys) {
        this.routingKeys = routingKeys;
    }
}
