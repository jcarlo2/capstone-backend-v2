package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
public class MerchandiseDiscountDTO {
    private String id;
    private Integer quantity;
    private Double discount;
}
