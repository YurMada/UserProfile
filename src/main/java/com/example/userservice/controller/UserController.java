package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    RabbitTemplate rabbitTemplate;

    static final String topicExchangeName = "user-service";
UserRepository userRepository;
    public UserController(UserService userService, RabbitTemplate rabbitTemplate) {
        this.userService = userService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("{id}")
    public Optional<User> getUser(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

   @PatchMapping("{id}")
    public ResponseEntity <User> updateUser(
        @PathVariable(value="id") Long id,
   @Valid @RequestBody User userDetails) throws ResourceAccessException{
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceAccessException("User not found on :: "+ id));

        user.setUserName(userDetails.getUserName());
        final User updatedUser = userRepository.save(user);
            String messageString = "Hello Rabbit @" + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME);
            rabbitTemplate.convertAndSend(topicExchangeName, "search-service", messageString);
            return ResponseEntity.ok(updatedUser);
}
}
