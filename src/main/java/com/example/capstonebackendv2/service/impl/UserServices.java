package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.entity.User;
import com.example.capstonebackendv2.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserServices {
    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verify(String username, @NotNull String password) {
        String pass = Base64.getEncoder().encodeToString(password.getBytes());
        return userRepository.existsUserByUsernameAndPasswordAndIsActive(username,pass,true);
    }

    public User getInfo(String username, @NotNull String password) {
        String pass = Base64.getEncoder().encodeToString(password.getBytes());
        return userRepository.findByUsernameAndPasswordAndIsActive(username,pass,true);
    }
}
