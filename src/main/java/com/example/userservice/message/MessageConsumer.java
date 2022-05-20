package com.example.userservice.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {

  @RabbitListener(queues = "search-service")
  public void consumeFirstQueueMessage(String message){
    System.out.println(  message);
  }


}