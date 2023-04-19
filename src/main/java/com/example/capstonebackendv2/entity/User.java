package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user", schema = "retail_management")
@Entity(name = "user")
public class User {
    @Id
    private String id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String password;
    private int role;
    @Column(insertable = false)
    private String timestamp;
    @Column(name = "is_active",insertable = false)
    private String isActive;

    @Transient
    private boolean isSave;
}
