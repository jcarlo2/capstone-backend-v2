package com.example.capstonebackendv2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "retail_management")
@Entity(name = "user")
public class User {
    @Id @Column(name = "id")
    private String username;
    private String password;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private int role;
    @Column(insertable = false)
    private String timestamp;
    @Column(name = "is_active",insertable = false)
    private Boolean isActive;

    @Transient
    private boolean isSave;
}
