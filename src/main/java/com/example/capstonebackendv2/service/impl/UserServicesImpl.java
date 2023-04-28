package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.entity.User;
import com.example.capstonebackendv2.repository.UserRepository;
import com.example.capstonebackendv2.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserService {
    private final UserRepository userRepository;

    public UserServicesImpl(UserRepository userRepository) {
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

    @Override
    public String generate(String id) {
        while(userRepository.existsByUsername(id)) id = String.valueOf(randomId());
        return id;
    }

    @Override
    public boolean create(User user) {
        if(user.getFirstName().equalsIgnoreCase("admin")
                || user.getLastName().equalsIgnoreCase("admin")) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    @Override
    public void changePassword(String id, String oldPassword, String newPassword) {
        userRepository.changePassword(id,oldPassword, newPassword);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAllByUsernameNotContainsAndFirstNameNotContainingAndLastNameNotContainingAndIsActive
                ("100000","admin","admin",true);
    }

    @Override
    public void archive(String id, String pass) {
        userRepository.archiveUserAccount(id);
    }

    @Override
    public String getLastname(String username) {
        Optional<User> user = userRepository.findById(username);
        return user.isPresent() ? user.get().getLastName() : "User";
    }
}
