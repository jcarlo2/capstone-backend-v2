package com.example.capstonebackendv2.service;

import com.example.capstonebackendv2.dto.ProductReportDTO;

import java.util.List;

public interface GenerateReportService {
    List<ProductReportDTO> generateSalesProductReport(String start, String end);
    List<ProductReportDTO> generateInventoryLossProductReport(String start, String end);
    List<ProductReportDTO> generateGoodsReceiptProductReport(String start, String end);
}
