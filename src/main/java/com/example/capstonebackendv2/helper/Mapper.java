package com.example.capstonebackendv2.mapper;

import com.example.capstonebackendv2.dto.MerchandiseDTO;
import com.example.capstonebackendv2.dto.UserDTO;
import com.example.capstonebackendv2.entity.Merchandise;
import com.example.capstonebackendv2.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Mapper {
    public List<MerchandiseDTO> productEntityToDTO(@NotNull List<Merchandise> list) {
        List<MerchandiseDTO> newList = new ArrayList<>();
        list.forEach(merchandise -> newList.add(new MerchandiseDTO(
                merchandise.getId(),
                merchandise.getDescription(),
                merchandise.getPrice(),
                merchandise.getQuantity()
        )));
        return newList;
    }

    public MerchandiseDTO productEntityToDTO(@NotNull Merchandise merchandise) {
        return new MerchandiseDTO(
                merchandise.getId(),
                merchandise.getDescription(),
                merchandise.getPrice(),
                merchandise.getQuantity()
        );
    }

    public UserDTO userEntityToDTO(@NotNull User user) {
        return new UserDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }
}
