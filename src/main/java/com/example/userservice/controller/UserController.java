package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("users")

public class UserController{
    UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    RabbitTemplate rabbitTemplate;

    static final String topicExchangeName = "user-service";


    public UserController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @GetMapping("{id}")
    public Optional<User> findUserById(@PathVariable String id) {
        logger.trace("vi loggar på DEBUG-nivå");

        return userService.findUserById(Long.valueOf(id));

    }

    @PutMapping("{id}")
     User update(@PathVariable(value = "id") @RequestBody User user) {
        userService.saveOrUpdate(user);
        String messageString = String.valueOf(user.getId());
        rabbitTemplate.convertAndSend(topicExchangeName, "user.updated", messageString);

        return user;
    }
}
