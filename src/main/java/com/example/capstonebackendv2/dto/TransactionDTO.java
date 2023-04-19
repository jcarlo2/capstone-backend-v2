package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class TransactionItemDTO {
    private String id;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private Double discount;
    private BigDecimal total;
    private String reportId;
}
