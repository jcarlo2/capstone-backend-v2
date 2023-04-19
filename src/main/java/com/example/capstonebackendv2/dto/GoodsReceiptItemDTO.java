package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter @ToString
@AllArgsConstructor
public class GoodsReceiptItemDTO {
    private String id;
    private String description;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal markPrice;
    private Double markPercent;
    private Boolean isMarkUp;
    private String reportId;
    private String expiration;
}
