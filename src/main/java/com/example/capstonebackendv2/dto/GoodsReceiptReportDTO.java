package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter @ToString
@AllArgsConstructor
public class GoodsReceiptReportDTO {
    private String id;
    private String user;
    private String timestamp;
    private Boolean isValid;
    private Boolean isArchived;
    private String reason;
    private String link;
}
