package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@ToString
public class TransactionItemDTO {
    private String id;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private Double discount;
    private BigDecimal total;
    private BigDecimal currentPrice;
    private String reportId;
}
