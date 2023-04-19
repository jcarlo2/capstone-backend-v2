package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @ToString
@AllArgsConstructor
public class InventoryLossReportDTO {
    private String id;
    private String user;
    private String timestamp;
    private BigDecimal total;
    private Boolean isValid;
    private Boolean isArchived;
    private String reason;
    private String link;
}
