package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @ToString
@AllArgsConstructor
public class TransactionRefundItemDTO {
    private String id;
    private String description;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal currentPrice;
    private BigDecimal total;
    private Double discount;
    private Integer damaged;
    private Integer exchange;
    private Integer expired;
    private Boolean isEdited;
    private String reportId;
}
