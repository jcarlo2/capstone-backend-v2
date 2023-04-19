package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.UserDTO;
import com.example.capstonebackendv2.facade.UserFacade;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get-info")
    public UserDTO getInfo(@RequestParam String username, @RequestParam String password) {
        return facade.getInfo(username,password);
    }
}
