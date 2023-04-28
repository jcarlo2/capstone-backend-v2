package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @ToString
@AllArgsConstructor
public class TransactionRefundReportDTO {
    private String user;
    private String oldId;
    private String newId;
    private BigDecimal oldTotal;
    private BigDecimal newTotal;
    private BigDecimal credit;
    private BigDecimal payment;
}
