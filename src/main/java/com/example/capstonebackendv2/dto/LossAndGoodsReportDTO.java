package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @ToString
@AllArgsConstructor
public class LossAndGoodsReportDTO {
    String id;
    String user;
    String timestamp;
    String reason;
    String link;
    BigDecimal total;
}
