package com.example.userservice;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class UserServiceApplication {

   // public static void main(String[] args) {
    //    SpringApplication.run(UserServiceApplication.class, args);

    //}

    @Bean
    public CommandLineRunner setUpUserForTesting(UserRepository userRepository) {
        return (args) -> {
            if (userRepository.findUserById("1") == null)
                userRepository.save(new User("madde"));
        };
    }
}