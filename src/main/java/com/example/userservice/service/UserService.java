package com.example.userservice.service;

import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service("test")
public class UserService {


    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserById(Long id) {
        return Optional.ofNullable(userRepository.findById(String.valueOf(id))
                .orElseThrow(() -> new EntityNotFoundException("Entity could not be found with id: " + id)));
    }

    public void updateUserDetail(User user){
        userRepository.save(user);

    }



}
