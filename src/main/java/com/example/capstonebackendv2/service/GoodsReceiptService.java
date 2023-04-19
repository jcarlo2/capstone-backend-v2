package com.example.capstonebackendv2.service;

import com.example.capstonebackendv2.dto.InventoryOption;
import com.example.capstonebackendv2.entity.GoodsReceiptItem;
import com.example.capstonebackendv2.entity.GoodsReceiptReport;

import java.util.List;

public interface GoodsReceiptService {
    String generateId(String id);
    void saveReportAndItems(GoodsReceiptReport report, List<GoodsReceiptItem> items);
    List<GoodsReceiptReport> findAllReports(InventoryOption option);
    List<GoodsReceiptItem> findAllItems(String id);
    GoodsReceiptReport findReportById(String id);
    void invalidate(String id);
}
