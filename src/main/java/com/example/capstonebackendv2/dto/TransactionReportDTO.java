package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @ToString
@AllArgsConstructor
public class TransactionReportDTO {
    private String id;
    private String user;
    private String timestamp;
    private Boolean isValid;
    private Boolean isArchived;
    private BigDecimal totalAmount;
    private BigDecimal credit;
    private BigDecimal payment;
}
