package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    RabbitTemplate rabbitTemplate;
    static final String topicExchangeName = "user-service";

    public UserController(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("{id}")
    public Optional<User> findUserById(@PathVariable Long id) {

        return userService.findUserById(id);
    }

    @PatchMapping("{id}")
    public User updateUser(@RequestBody User user) {
        userService.updateUserDetail(user);
        String messageString = "Hello Rabbit @" + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME);
        rabbitTemplate.convertAndSend(topicExchangeName, "search-service", messageString);
        return user;

    }
}
