package com.example.capstonebackendv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@ToString
public class TransactionDTO {
    private List<TransactionItemDTO> itemList;
    private TransactionReportDTO report;
}
