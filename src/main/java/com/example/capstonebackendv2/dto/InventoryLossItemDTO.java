package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @ToString
@AllArgsConstructor
public class InventoryLossItemDTO {
    private String id;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal total;
    private String reportId;
    private String link;
    private String reason;
}
