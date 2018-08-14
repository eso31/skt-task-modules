package com.skytask;

import com.skytask.listener.ListenerRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MicroserviceApp {
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceApp.class, args);
    }

    @Bean
    public ListenerRabbit subscriber(){
        return new ListenerRabbit();
    }
}
