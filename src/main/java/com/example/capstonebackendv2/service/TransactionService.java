package com.example.capstonebackendv2.service;

import com.example.capstonebackendv2.dto.ProductReportDTO;
import com.example.capstonebackendv2.entity.TransactionReport;
import com.example.capstonebackendv2.entity.TransactionReportItem;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    String generateId(String id);
    List<TransactionReport> findAllReportByCriteria(Boolean valid, Boolean archived, String id, String start, String end, int size);
    TransactionReport findReportById(String id);
    List<TransactionReportItem> findItems(String id);
    void save(List<TransactionReportItem> itemList, TransactionReport transactionReport);
    void invalidate(String id);
    int countActiveReportInBetween(String start, String end);
    BigDecimal getAnnualBreakDown(List<ProductReportDTO> reports);
}
