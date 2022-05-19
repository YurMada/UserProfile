package com.example.userservice.controller;

import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import com.github.dockerjava.api.exception.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("users")

public class UserController {


    UserService userService;
    Logger logger = LoggerFactory.getLogger(UserController.class);
    RabbitTemplate rabbitTemplate;

    static final String topicExchangeName = "user-service";


    public UserController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("signup")
    public ResponseEntity createUser(@RequestBody User user) {
        if (user == null)
            throw new BadRequestException("This field cannot be empty");
        User newUser = userService.createUser(user);
        logger.trace("vi loggar på DEBUG-nivå ");
        return new ResponseEntity(newUser, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public Optional<User> findUserById(@PathVariable String id) {
        logger.trace("vi loggar på DEBUG-nivå");

        return userService.findUserById(Long.valueOf(id));

    }

    @PutMapping("{id}")
     User update(@PathVariable(value = "id") @RequestBody User user) {
        userService.saveOrUpdate(user);
        String messageString = "Updated user";
        rabbitTemplate.convertAndSend(topicExchangeName, "search-service", messageString);

        return user;
    }
//    @PatchMapping("{id}")
//    public ResponseEntity<User> updateUser(
//            @PathVariable(value = "id") Long id,
//            @Valid @RequestBody User userDetails) throws ResourceAccessException {
//        User user = userService.findUserById(id)
//                .orElseThrow(() -> new ResourceAccessException("User not found on :: " + id));
//        user.setUserName(userDetails.getUserName());
//        final User updatedUser = userService.saveOrUpdate(user);
//        logger.trace("vi loggar på DEBUG-nivå");
//      //  String messageString = "Hello Rabbit @" + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME);
//     //   rabbitTemplate.convertAndSend(topicExchangeName, "search-service", messageString);
//        return ResponseEntity.ok(updatedUser);
//    }
}
