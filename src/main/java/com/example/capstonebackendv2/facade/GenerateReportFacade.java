package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.ProductReportDTO;
import com.example.capstonebackendv2.service.impl.DateServiceImpl;
import com.example.capstonebackendv2.service.impl.GenerateReportServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenerateReportFacade {
    private final GenerateReportServiceImpl service;
    private final DateServiceImpl dateService;

    public GenerateReportFacade(GenerateReportServiceImpl service, DateServiceImpl dateService) {
        this.service = service;
        this.dateService = dateService;
    }

    public List<ProductReportDTO> generateSalesProductReport(@NotNull String start, String option) {
        if(start.equals("")) return new ArrayList<>();
        start = dateService.fixStartDate(start,option);
        String end = dateService.addDays(start, option);
        return service.generateSalesProductReport(start,end);
    }

    public List<ProductReportDTO> generateInventoryLossProductReport(@NotNull String start, String option) {
        if(start.equals("")) return new ArrayList<>();
        start = dateService.fixStartDate(start,option);
        String end = dateService.addDays(start, option);
        return service.generateInventoryLossProductReport(start,end);
    }

    public List<ProductReportDTO> generateGoodsReceiptProductReport(@NotNull String start, String option) {
        if(start.equals("")) return new ArrayList<>();
        start = dateService.fixStartDate(start,option);
        String end = dateService.addDays(start, option);
        return service.generateGoodsReceiptProductReport(start,end);
    }
}
