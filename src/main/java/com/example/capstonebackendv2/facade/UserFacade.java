package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.UserDTO;
import com.example.capstonebackendv2.entity.User;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.UserServicesImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class UserFacade {
    private final UserServicesImpl service;
    private final Mapper mapper;

    public UserFacade(UserServicesImpl service, Mapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public boolean verify(String username, String password) {
        return service.verify(username, password);
    }

    public UserDTO getInfo(String username, String password) {
        User user = service.getInfo(username,password);
        return mapper.userEntityToDTO(user);
    }

    public List<UserDTO> findAll() {
        return service.findAll().stream().map(mapper::userEntityToDTO).toList();
    }

    public String generate(String id) {
        return service.generate(id);
    }

    public void create(UserDTO user) {
        service.create(mapper.userDTOToEntity(user));
    }

    public boolean changePassword(String id, @NotNull String oldPassword, @NotNull String newPassword) {
        String oldPass = Base64.getEncoder().encodeToString(oldPassword.getBytes());
        String newPass = Base64.getEncoder().encodeToString(newPassword.getBytes());
        if(service.verify(id,oldPassword)) {
            service.changePassword(id,oldPass,newPass);
            return true;
        }
        return false;
    }

    public boolean archive(@NotNull String id, @NotNull String password) {
        if(id.equals("100000")) return false;
        String pass = Base64.getEncoder().encodeToString(password.getBytes());
        if(service.verify("100000",password)) {
            service.archive(id,pass);
            return true;
        }
        return false;
    }
}
