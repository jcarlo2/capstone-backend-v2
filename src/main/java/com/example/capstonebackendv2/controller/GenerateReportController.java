package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.ProductReportDTO;
import com.example.capstonebackendv2.facade.GenerateReportFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/report")
public class GenerateReportController {
    private final GenerateReportFacade facade;

    public GenerateReportController(GenerateReportFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/sales-product-report")
    public List<ProductReportDTO> generateSalesProductReport(@RequestParam String start, @RequestParam String option) {
        return facade.generateSalesProductReport(start,option);
    }

    @GetMapping("/loss-product-report")
    public List<ProductReportDTO> generateInventoryLossProductReport(@RequestParam String start, @RequestParam String option) {
        return facade.generateInventoryLossProductReport(start,option);
    }

    @GetMapping("/goods-product-report")
    public List<ProductReportDTO> generateGoodsReceiptProductReport(@RequestParam String start, @RequestParam String option) {
        return facade.generateGoodsReceiptProductReport(start,option);
    }
}


