package com.example.capstonebackendv2.entity;

import lombok.*;

import jakarta.persistence.*;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log", schema = "retail_management")
@Entity(name = "log")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String no;
    private String user;
    private String action;
    private String description;
    @Column(name = "date_time", insertable = false)
    private String timestamp;
    @Column(name = "is_deletable")
    private Boolean isDeletable;
}
