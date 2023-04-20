package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductReportDTO {
    private String id;
    private String name;
    private Integer quantity;
    private Double discount;
    private BigDecimal price;
    private BigDecimal total;
    private String reason;
}
