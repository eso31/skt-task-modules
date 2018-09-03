package com.skytask.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("rabbitmq.config")
public class RabbitMQVariables {
    public static class Queue {
        private String responseProductList;
        private String requestProductList;
        private String createProduct;

        public String getResponseProductList() {
            return responseProductList;
        }

        public void setResponseProductList(String responseProductList) {
            this.responseProductList = responseProductList;
        }

        public String getRequestProductList() {
            return requestProductList;
        }

        public void setRequestProductList(String requestProductList) {
            this.requestProductList = requestProductList;
        }

        public String getCreateProduct() {
            return createProduct;
        }

        public void setCreateProduct(String createProduct) {
            this.createProduct = createProduct;
        }
    }

    public static class RoutingKey{
        private String responseProductList;
        private String requestProductList;
        private String createProduct;

        public String getResponseProductList() {
            return responseProductList;
        }

        public void setResponseProductList(String responseProductList) {
            this.responseProductList = responseProductList;
        }

        public String getRequestProductList() {
            return requestProductList;
        }

        public void setRequestProductList(String requestProductList) {
            this.requestProductList = requestProductList;
        }

        public String getCreateProduct() {
            return createProduct;
        }

        public void setCreateProduct(String createProduct) {
            this.createProduct = createProduct;
        }
    }


    private Queue queue;
    private String exchange;
    private RoutingKey routingKey;


    public Queue getQueue() {
        return queue;
    }

    public void setQueue(Queue queue) {
        this.queue = queue;
    }
    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public RoutingKey getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(RoutingKey routingKey) {
        this.routingKey = routingKey;
    }
}
