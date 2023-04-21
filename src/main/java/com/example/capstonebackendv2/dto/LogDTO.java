package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
public class LogDTO {
    private String num;
    private String user;
    private String action;
    private String description;
    private String timestamp;
}
