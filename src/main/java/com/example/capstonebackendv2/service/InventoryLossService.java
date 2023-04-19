package com.example.capstonebackendv2.service;

import com.example.capstonebackendv2.dto.InventoryOption;
import com.example.capstonebackendv2.entity.InventoryLossItem;
import com.example.capstonebackendv2.entity.InventoryLossReport;

import java.util.List;

public interface InventoryLossService {
    String generateId(String id);
    void saveReportAndItems(InventoryLossReport report,List<InventoryLossItem> items);
    List<InventoryLossReport> findAllReports(InventoryOption option);
    List<InventoryLossItem> findAllItems(String id);
    InventoryLossReport findReportById(String id);
    boolean invalidate(String id);
}
