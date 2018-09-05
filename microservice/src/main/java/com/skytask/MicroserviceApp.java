package com.skytask;

import com.skytask.listener.Listener;
import com.skytask.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroserviceApp {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApp.class, args);
    }

    @Autowired
    private ProductService productService;

    @Bean
    public Listener listener(){
        return new Listener(productService);
    }
}
