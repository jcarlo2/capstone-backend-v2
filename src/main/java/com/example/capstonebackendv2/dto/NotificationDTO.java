package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
public class NotificationDTO {
    private Integer key;
    private String id;
    private String description;
    private Integer quantity;
    private String reason;
    private String color;
}
