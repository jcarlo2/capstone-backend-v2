package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.dto.ProductReportDTO;
import com.example.capstonebackendv2.entity.*;
import com.example.capstonebackendv2.repository.*;
import com.example.capstonebackendv2.service.GenerateReportService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class GenerateReportServiceImpl implements GenerateReportService {

    private final TransactionReportRepository transactionReportRepository;
    private final TransactionItemRepository transactionItemRepository;
    private final InventoryLossReportRepository inventoryLossReportRepository;
    private final InventoryLossItemRepository inventoryLossItemRepository;
    private final GoodsReceiptReportRepository goodsReceiptReportRepository;
    private final GoodsReceiptItemRepository goodsReceiptItemRepository;

    public GenerateReportServiceImpl(TransactionReportRepository transactionReportRepository, TransactionItemRepository transactionItemRepository,
                                    InventoryLossReportRepository inventoryLossReportRepository, InventoryLossItemRepository inventoryLossItemRepository,
                                     GoodsReceiptReportRepository goodsReceiptReportRepository, GoodsReceiptItemRepository goodsReceiptItemRepository) {
        this.transactionReportRepository = transactionReportRepository;
        this.transactionItemRepository = transactionItemRepository;
        this.inventoryLossReportRepository = inventoryLossReportRepository;
        this.inventoryLossItemRepository = inventoryLossItemRepository;
        this.goodsReceiptReportRepository = goodsReceiptReportRepository;
        this.goodsReceiptItemRepository = goodsReceiptItemRepository;
    }

    @Override
    public List<ProductReportDTO> generateSalesProductReport(@NotNull String start, String end) {
        List<ProductReportDTO> productReportDTOList = new ArrayList<>();
        HashMap<String, ProductReportDTO> reportMap = new HashMap<>();
        List<TransactionReport> reportList =
                transactionReportRepository.findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(true,start,end);
        reportList.forEach(report -> {
            List<TransactionReportItem> itemList = transactionItemRepository.findAllByUniqueId(report.getId());
            itemList.forEach(item -> {
                String unique = item.getProductId() + item.getName() + item.getPrice();
                if(reportMap.containsKey(unique)) {
                    ProductReportDTO temp = reportMap.get(unique);
                    temp.setQuantity(temp.getQuantity() + item.getSold());
                    temp.setTotal(temp.getPrice().multiply(new BigDecimal(temp.getQuantity())));
                }else reportMap.put(unique,new ProductReportDTO(
                        item.getProductId(),
                        item.getName(),
                        item.getSold(),
                        item.getDiscountPercentage(),
                        item.getPrice(),
                        item.getTotalAmount(),
                        ""
                ));
            });
        });

        for(Map.Entry<String, ProductReportDTO> temp : reportMap.entrySet()) productReportDTOList.add(temp.getValue());
        productReportDTOList.sort(Comparator.comparing(ProductReportDTO::getId));
        return productReportDTOList;
    }

    @Override
    public List<ProductReportDTO> generateInventoryLossProductReport(String start, String end) {
        List<ProductReportDTO> productReportDTOList = new ArrayList<>();
        HashMap<String, ProductReportDTO> reportMap = new HashMap<>();
        List<InventoryLossReport> reportList = inventoryLossReportRepository.
                findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(true,start,end);
        reportList.forEach(report -> {
            List<InventoryLossItem> itemList = inventoryLossItemRepository.findAllByReportIdOrderByNum(report.getId());
            itemList.forEach(item -> {
                String unique = item.getId() + item.getName() + item.getReason();
                if(reportMap.containsKey(unique)) {
                    ProductReportDTO temp = reportMap.get(unique);
                    temp.setQuantity(temp.getQuantity() + item.getQuantity());
                    temp.setTotal(temp.getPrice().multiply(new BigDecimal(temp.getQuantity())));
                }else reportMap.put(unique,new ProductReportDTO(
                        item.getId(),
                        item.getName(),
                        item.getQuantity(),
                        0.0,
                        item.getPrice(),
                        item.getTotalAmount(),
                        item.getReason()
                ));
            });
        });

        for(Map.Entry<String, ProductReportDTO> temp : reportMap.entrySet()) productReportDTOList.add(temp.getValue());
        productReportDTOList.sort(Comparator.comparing(ProductReportDTO::getId));
        return productReportDTOList;
    }

    @Override
    public List<ProductReportDTO> generateGoodsReceiptProductReport(String start, String end) {
        List<ProductReportDTO> productReportDTOList = new ArrayList<>();
        HashMap<String, ProductReportDTO> reportMap = new HashMap<>();
        List<GoodsReceiptReport> reportList = goodsReceiptReportRepository.
                findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(true,start,end);
        reportList.forEach(report -> {
            List<GoodsReceiptItem> itemList = goodsReceiptItemRepository.findAllByUniqueIdOrderByNum(report.getId());
            itemList.forEach(item -> {
                String unique = item.getProductId() + item.getName() + item.getPrice();
                if(reportMap.containsKey(unique)) {
                    ProductReportDTO temp = reportMap.get(unique);
                    temp.setQuantity(temp.getQuantity() + item.getQuantity());
                    temp.setTotal(temp.getPrice().multiply(new BigDecimal(temp.getQuantity())));
                }else reportMap.put(unique,new ProductReportDTO(
                        item.getProductId(),
                        item.getName(),
                        item.getQuantity(),
                        0.0,
                        item.getPrice(),
                        BigDecimal.ZERO,
                        ""
                ));
            });
        });

        for(Map.Entry<String, ProductReportDTO> temp : reportMap.entrySet()) productReportDTOList.add(temp.getValue());
        productReportDTOList.sort(Comparator.comparing(ProductReportDTO::getId));
        return productReportDTOList;
    }
}
