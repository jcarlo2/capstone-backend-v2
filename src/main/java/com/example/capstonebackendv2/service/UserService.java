package com.example.capstonebackendv2.service;

import com.example.capstonebackendv2.entity.User;

import java.util.List;

public interface UserService {
    boolean verify(String username, String password);
    User getInfo(String username, String password);
    String generate(String id);
    void create(User user);
    void changePassword(String id, String oldPassword, String newPassword);
    List<User> findAll();
    void archive(String id, String pass);

    default int randomId() {
        int max = 999999;
        int min = 100000;
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
    }
}
