package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.UserDTO;
import com.example.capstonebackendv2.facade.UserFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserFacade facade;

    public UserController(UserFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/verify")
    public boolean verify(@RequestParam String username, @RequestParam String password) {
        return facade.verify(username,password);
    }

    @GetMapping("verify-admin")
    public boolean verifyAdmin(@RequestParam String password) {
        return facade.verifyAdmin(password);
    }

    @GetMapping("/get-info")
    public UserDTO getInfo(@RequestParam String username, @RequestParam String password) {
        return facade.getInfo(username,password);
    }

    @GetMapping("get-lastname")
    public String getLastname(@RequestParam String username) {
        return facade.getLastname(username);
    }

    @GetMapping("/user-list")
    public List<UserDTO> findAll() {
        return facade.findAll();
    }

    @GetMapping("/random-id")
    public String generate(@RequestParam String id) {
        return facade.generate(id);
    }

    @PostMapping("/create")
    public boolean create(@RequestBody UserDTO user) {
        return facade.create(user);
    }

    @GetMapping("/change-password")
    public boolean changePassword(@RequestParam String id, @RequestParam String oldPassword, @RequestParam String newPassword) {
        return facade.changePassword(id,oldPassword,newPassword);
    }

    @GetMapping("/archive")
    public boolean archive(@RequestParam String id, @RequestParam String password) {
        return facade.archive(id, password);
    }
}
