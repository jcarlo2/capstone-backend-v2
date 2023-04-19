package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.UserDTO;
import com.example.capstonebackendv2.entity.User;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.UserServices;
import org.springframework.stereotype.Service;

@Service
public class UserFacade {
    private final UserServices services;
    private final Mapper mapper;

    public UserFacade(UserServices services, Mapper mapper) {
        this.services = services;
        this.mapper = mapper;
    }

    public boolean verify(String username, String password) {
        return services.verify(username, password);
    }

    public UserDTO getInfo(String username, String password) {
        User user = services.getInfo(username,password);
        return mapper.userEntityToDTO(user);
    }
}
