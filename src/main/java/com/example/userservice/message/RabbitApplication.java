package com.example.userservice.message;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
@EnableScheduling
public class RabbitApplication {

    static final String topicExchangeName = "user-service";

    private final RabbitTemplate rabbitTemplate;


    public RabbitApplication(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public static void main(String[] args) {
       SpringApplication.run(RabbitApplication.class, args);
   }

    //@Scheduled(fixedDelay = 5000)
    public void pushMessage() {
        String messageString = "Hello Rabbit @" + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME);
        rabbitTemplate.convertAndSend(topicExchangeName, "search-service", messageString);
    }
}