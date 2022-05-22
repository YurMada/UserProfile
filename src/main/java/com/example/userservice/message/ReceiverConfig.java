package com.example.userservice.message;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ReceiverConfig {

    static final String exchangeName = "auth_message_exchange";


    @Bean
    Queue queue() {
      return new Queue("user-service", false);
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("user.new");
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, Queue queue) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(queue);
        simpleMessageListenerContainer.setMessageListener(new Receiver());
        return simpleMessageListenerContainer;
    }
}