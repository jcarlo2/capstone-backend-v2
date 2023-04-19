package com.example.capstonebackendv2.dto;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class InventoryOption {
    private String type;
    private String category;
    private Boolean isValid;
    private Boolean isArchived;
    private String search;
    private String start;
    private String end;
    private Integer size;
}
