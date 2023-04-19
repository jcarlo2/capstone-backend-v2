package com.capstone.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "log_history", schema = "retail_management")
@Entity(name = "log_history")
public class LogHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String no;
    private String user;
    private String action;
    private String description;
    @Column(name = "archived_at")
    private String archivedAt;
    @Column(name = "created_at", insertable = false)
    private String createdAt;
}
