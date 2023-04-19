package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class UserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private int role;
}
