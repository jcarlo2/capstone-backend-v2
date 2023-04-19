package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter @ToString
@AllArgsConstructor
public class GoodsReceiptDTO {
    List<GoodsReceiptItemDTO> itemList;
    GoodsReceiptReportDTO report;
}
