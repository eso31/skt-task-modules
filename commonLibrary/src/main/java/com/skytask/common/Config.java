package com.skytask.common;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableRabbit
@Configuration
public class Config {

    @Value("${rabbitmq.config.queue.responseProductList:responseProductListQ}")
    private String responseProductList;

    @Value("${rabbitmq.config.queue.requestProductList:requestProductListQ}")
    private String requestProductList;

    @Value("${rabbitmq.config.queue.createProduct:createProductQ}")
    private String createProduct;

    @Value("${rabbitmq.config.exchange:productExchange}")
    private String exchange;

    @Value("${rabbitmq.config.routingKey.responseProductList:responseProductListKey}")
    private String responseProductListKey;

    @Value("${rabbitmq.config.routingKey.requestProductList:requestProductListKey}")
    private String requestProductListKey;

    @Value("${rabbitmq.config.routingKey.createProduct:createProductKey}")
    private String createProductKey;

    @Bean
    public Executor taskExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleMessageListenerContainerFactory(ConnectionFactory connectionFactory,
                                                                                      SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setTaskExecutor(taskExecutor());
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setReplyTimeout(15000);

        return template;
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Queue responseProductListQueue() {
        return new Queue(responseProductList);
    }

    @Bean
    public Queue requestProductListQueue() {
        return new Queue(requestProductList);
    }

    @Bean
    public Queue createProductQueue() {
        return new Queue(createProduct);
    }

    @Bean
    public SimpleMessageListenerContainer rpcReplyMessageListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setQueues(responseProductListQueue());
        simpleMessageListenerContainer.setTaskExecutor(taskExecutor());
        return simpleMessageListenerContainer;
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(ConnectionFactory connectionFactory) {

        AsyncRabbitTemplate asyncRabbitTemplate = new AsyncRabbitTemplate(
                rabbitTemplate(connectionFactory),
                rpcReplyMessageListenerContainer(connectionFactory), exchange + "/" + responseProductListKey);

        return asyncRabbitTemplate;
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public List<Binding> bindings() {
        return Arrays.asList(
                BindingBuilder.bind(requestProductListQueue()).to(directExchange()).with(requestProductListKey),
                BindingBuilder.bind(responseProductListQueue()).to(directExchange()).with(responseProductListKey),
                BindingBuilder.bind(createProductQueue()).to(directExchange()).with(createProductKey)
        );

    }


}
