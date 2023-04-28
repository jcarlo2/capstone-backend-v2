package com.example.capstonebackendv2.helper;

import com.example.capstonebackendv2.dto.*;
import com.example.capstonebackendv2.entity.*;
import com.example.capstonebackendv2.helper.enums.Category;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class Mapper {
    public List<MerchandiseDTO> merchandiseEntityToDTO(@NotNull List<Merchandise> list) {
        List<MerchandiseDTO> newList = new ArrayList<>();
        list.forEach(merchandise -> newList.add(new MerchandiseDTO(
                merchandise.getId(),
                merchandise.getDescription(),
                merchandise.getPrice(),
                merchandise.getQuantity(),
                merchandise.getPiecesPerBox()
        )));
        return newList;
    }

    public MerchandiseDTO merchandiseEntityToDTO(@NotNull Merchandise merchandise) {
        return new MerchandiseDTO(
                merchandise.getId(),
                merchandise.getDescription(),
                merchandise.getPrice(),
                merchandise.getQuantity(),
                merchandise.getPiecesPerBox()
        );
    }

    public UserDTO userEntityToDTO(@NotNull User user) {
        return new UserDTO(
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole()
        );
    }

    public List<TransactionReportItem> transactionDTOToItemEntity(@NotNull List<TransactionItemDTO> list) {
        List<TransactionReportItem> itemList = new ArrayList<>();
        list.forEach(item -> itemList.add(new TransactionReportItem(
                "",
                item.getId(),
                item.getDescription(),
                item.getPrice(),
                item.getQuantity(),
                item.getPrice().multiply(new BigDecimal(item.getQuantity())),
                item.getDiscount(),
                item.getTotal(),
                item.getReportId()
        )));
        return itemList;
    }

    public TransactionReport transactionDTOToReportEntity(@NotNull TransactionReportDTO report) {
        return new TransactionReport(
                report.getId(), report.getUser(), "","",
                true,false,report.getTotalAmount(),report.getCredit(),report.getPayment()
        );
    }

    public List<TransactionItemDTO> transactionReportItemToDTO(
            @NotNull List<TransactionReportItem> items,
            @NotNull List<Merchandise> merchandiseList,
            String reportId) {
        Map<String, Merchandise> merchandiseMap = merchandiseList.stream().collect(Collectors.toMap(Merchandise::getId, Function.identity()));
        return items
                .stream()
                .filter(item -> merchandiseMap.containsKey(item.getProductId()))
                .map(item -> {
                    Merchandise merchandise = merchandiseMap.get(item.getProductId());
                    return new TransactionItemDTO(
                            item.getProductId(),
                            item.getName(),
                            item.getPrice(),
                            item.getSold(),
                            item.getDiscountPercentage(),
                            item.getTotalAmount(),
                            merchandise.getPrice(),
                            reportId
                    );
                }).collect(Collectors.toList());
    }

    public TransactionReportDTO transactionReportToTransactionReportDTO(@NotNull TransactionReport report) {
        return new TransactionReportDTO(
                report.getId(),
                report.getUser(),
                report.getTimestamp(),
                report.getIsValid(),
                report.getIsArchived(),
                report.getTotalAmount(),
                report.getCredit(),
                report.getPayment()
        );
    }

    public List<InventoryLossItem> inventoryLossDTOToItemEntity(@NotNull List<InventoryLossItemDTO> items) {
        return items
                .stream()
                .map(item -> new InventoryLossItem(
                        "",
                        item.getId(),
                        item.getDescription(),
                        item.getPrice(),
                        item.getQuantity(),
                        0.0,
                        new BigDecimal(item.getQuantity()).multiply(item.getPrice()),
                        item.getReportId(),
                        item.getReason()
                )).collect(Collectors.toList());
    }

    public InventoryLossReport inventoryLossDTOToReportEntity(@NotNull InventoryLossReportDTO report) {
        return new InventoryLossReport(
                report.getId(),
                report.getUser(),
                report.getTotal(),
                "",
                "",
                report.getLink(),
                report.getIsValid(),
                report.getIsArchived(),
                report.getReason()
        );
    }

    public List<GoodsReceiptItem> goodsReceiptDTOToItemEntity(@NotNull List<GoodsReceiptItemDTO> list) {
        return list
                .stream()
                .map(item -> new GoodsReceiptItem(
                        "",
                        item.getId(),
                        item.getDescription(),
                        item.getQuantity(),
                        item.getPrice(),
                        item.getMarkPrice(),
                        item.getMarkPercent(),
                        item.getIsMarkUp(),
                        item.getReportId()
                )).collect(Collectors.toList());
    }

    public GoodsReceiptReport goodsReceiptDTOToReportEntity(@NotNull GoodsReceiptReportDTO report) {
        return new GoodsReceiptReport(
                report.getId(),
                report.getUser(),
                "", "",
                true, false,
                report.getReason(),
                report.getLink()
        );
    }

    public List<MerchandiseExpiration> goodsReceiptItemDTOToExpirationEntity(@NotNull List<GoodsReceiptItemDTO> list) {
        return list
                .stream()
                .map(item -> new MerchandiseExpiration(
                    "",
                    item.getId(),
                    item.getDescription(),
                    item.getExpiration() + " 23:59:59",
                    "1",
                    item.getQuantity(),
                    item.getReportId()
                )).collect(Collectors.toList());
    }

    public List<LossAndGoodsReportDTO> inventoryLossReportListToDTO(@NotNull List<InventoryLossReport> reportList) {
        return reportList
                .stream()
                .map(item -> new LossAndGoodsReportDTO(
                    item.getId(),
                    item.getUser(),
                    item.getTimestamp(),
                    item.getReason(),
                    item.getLink(),
                    item.getTotal()
                )).collect(Collectors.toList());
    }

    public List<LossAndGoodsReportDTO> goodsReceiptReportListToDTO(@NotNull List<GoodsReceiptReport> reportList) {
        return reportList
                .stream()
                .map(item -> new LossAndGoodsReportDTO(
                        item.getId(),
                        item.getUser(),
                        item.getTimestamp(),
                        item.getReason(),
                        item.getLink(),
                        BigDecimal.ZERO
                )).collect(Collectors.toList());
    }

    public List<InventoryLossItemDTO> inventoryLossItemEntityToDTO(@NotNull List<InventoryLossItem> itemList) {
        return itemList
                .stream()
                .map(item -> new InventoryLossItemDTO(
                    item.getId(),
                    item.getName(),
                    item.getQuantity(),
                    item.getPrice(),
                    item.getTotalAmount(),
                    item.getReportId(),
                    "",
                    item.getReason()
                )).collect(Collectors.toList());
    }

    public List<GoodsReceiptItemDTO> goodsReceiptItemEntityToDTO(@NotNull List<GoodsReceiptItem> itemList) {
        return itemList
                .stream()
                .map(item -> new GoodsReceiptItemDTO(
                        item.getProductId(),
                        item.getName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getMarkPrice(),
                        item.getMarkPercent(),
                        item.getIsMarkUp(),
                        item.getUniqueId(),
                        ""
                )).collect(Collectors.toList());
    }

    public GoodsReceiptReportDTO goodsReceiptReportToDTO(@NotNull GoodsReceiptReport report) {
        return new GoodsReceiptReportDTO(
                report.getId(),
                report.getUser(),
                report.getTimestamp(),
                report.getIsValid(),
                report.getIsArchived(),
                report.getReason(),
                report.getLink()
        );
    }

    public InventoryLossReportDTO inventoryLossReportToDTO(@NotNull InventoryLossReport report) {
        return new InventoryLossReportDTO(
                report.getId(),
                report.getUser(),
                report.getTimestamp(),
                report.getTotal(),
                report.getIsValid(),
                report.getIsArchived(),
                report.getReason(),
                report.getLink()
        );
    }

    public List<MerchandiseDiscountDTO> discountEntityToDTO(@NotNull List<MerchandiseDiscount> list) {
        List<MerchandiseDiscountDTO> newList = new ArrayList<>();
        list.forEach(item -> newList.add(new MerchandiseDiscountDTO(item.getId(),item.getQuantity(),item.getDiscount())));
        return newList;
    }

    public Merchandise merchandiseDTOToEntity(@NotNull MerchandiseDTO dto) {
        return new Merchandise(
                dto.getId(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getQuantity(),
                dto.getPiecesPerBox(),
                0.0,true,0.0,0.0,true
        );
    }

    public List<MerchandiseCategory> merchandiseCategoryDTOToEntity(@NotNull List<MerchandiseCategoryDTO> categoryList) {
        return categoryList
                .stream()
                .map(dto -> new MerchandiseCategory("",dto.getId(), dto.getCategory()))
                .collect(Collectors.toList());
    }

    public MerchandiseDiscount discountDTOToEntity(@NotNull MerchandiseDiscountDTO discount) {
        return new MerchandiseDiscount("", discount.getId(), discount.getDiscount(), discount.getQuantity(),true);
    }

    public List<MerchandiseHistoryDTO> merchandiseHistoryEntityToDTO(@NotNull List<MerchandiseHistory> list) {
        return list
                .stream()
                .map(item -> new MerchandiseHistoryDTO(

                ))
                .collect(Collectors.toList());
    }

    public List<MerchandiseCategory> addProductCategoryListToEntity(@NotNull List<Category> categoryList, String id) {
        return categoryList
                .stream()
                .map(category -> new MerchandiseCategory(
                        "", id, category
                )).collect(Collectors.toList());
    }

    public List<InventoryLossItem> refundItemsToInventoryLossItemEntity(@NotNull List<TransactionRefundItemDTO> itemList, String lossId) {
        return itemList.stream().flatMap(item -> {
            if(item.getIsEdited() && (item.getExpired() > 0 || item.getDamaged() > 0)) {
                if(item.getDamaged() > 0 && item.getExpired() > 0) {
                    return Stream.of(
                            new InventoryLossItem("",item.getId(),item.getDescription(),item.getPrice(),
                                    item.getDamaged(),item.getDiscount(),item.getTotal(),lossId,"Damaged"),
                            new InventoryLossItem("",item.getId(),item.getDescription(),item.getPrice(),
                                    item.getExpired(),item.getDiscount(),item.getTotal(),lossId,"Expired")
                    );
                } else if(item.getExpired() > 0) {
                    return Stream.of(
                            new InventoryLossItem("",item.getId(),item.getDescription(),item.getPrice(),
                                    item.getExpired(),item.getDiscount(),item.getTotal(),lossId,"Expired")
                    );
                }else return Stream.of(
                        new InventoryLossItem("",item.getId(),item.getDescription(),item.getPrice(),
                                item.getDamaged(),item.getDiscount(),item.getTotal(),lossId,"Damaged")
                );
            }
            return Stream.empty();
        }).collect(Collectors.toList());
    }

    public List<GoodsReceiptItem> refundItemsToGoodsReceiptItemEntity(@NotNull List<TransactionRefundItemDTO> itemList, String goodsId) {
        return itemList.stream()
                .map(item -> {
                    if(item.getIsEdited() && item.getExchange() > 0) {
                        return new GoodsReceiptItem(
                                "",item.getId(),item.getDescription(),item.getExchange(),item.getPrice(),
                                BigDecimal.ZERO,0.0,true,goodsId
                        );
                    }
                    return null;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public InventoryLossReport refundItemsToInventoryLossReportEntity(@NotNull TransactionRefundDTO dto, String lossId) {
        BigDecimal total = dto.getItemList().stream().map(TransactionRefundItemDTO::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
        return new InventoryLossReport(
                lossId,
                dto.getReport().getUser(),
                total,
                "","",
                dto.getReport().getNewId(),
                true,false,
                "Transaction Return"
        );
    }

    public GoodsReceiptReport refundItemsToGoodsReceiptReportEntity(@NotNull TransactionRefundDTO dto, String goodsId) {
        return new GoodsReceiptReport(
                goodsId,
                dto.getReport().getUser(),
                "","",
                true,false,
                "Transaction Return",
                dto.getReport().getNewId()
        );
    }

    public TransactionReport refundItemsToTransactionReportEntity(@NotNull TransactionRefundDTO dto) {
        BigDecimal total = dto.getItemList().stream().map(TransactionRefundItemDTO::getTotal).reduce(BigDecimal.ZERO,BigDecimal::add);
        return new TransactionReport(
                dto.getReport().getNewId(),
                dto.getReport().getUser(),
                "","",
                true,false,
                total,
                dto.getReport().getCredit(),
                dto.getReport().getPayment()
        );
    }

    public List<TransactionReportItem> refundItemsToTransactionItemEntity(@NotNull List<TransactionRefundItemDTO> itemList, String newId) {
        return itemList.stream().map(item -> new TransactionReportItem(
                "",
                item.getId(),
                item.getDescription(),
                item.getPrice(),
                item.getQuantity(),
                item.getPrice().multiply(new BigDecimal(item.getQuantity())),
                item.getDiscount(),
                item.getTotal(),
                newId
        )).collect(Collectors.toList());
    }

    public List<LogDTO> logsEntityToDTO(@NotNull List<Log> show) {
        return show.stream().map(item -> new LogDTO(
                item.getNo(),
                item.getUser(),
                item.getAction(),
                item.getDescription(),
                item.getTimestamp()
        )).collect(Collectors.toList());
    }

    public Log logDTOToEntity(@NotNull LogDTO dto) {
        return new Log(
                "",
                dto.getUser(),
                dto.getAction(),
                dto.getDescription(),
                "",
                true,false
        );
    }

    public MerchandiseDiscountHistory merchandiseDTOToHistoryEntity(@NotNull MerchandiseDiscountDTO dto) {
        return new MerchandiseDiscountHistory(
                "",
                dto.getId(),
                dto.getDiscount(),
                dto.getQuantity(),
                ""
        );
    }

    public User userDTOToEntity(@NotNull UserDTO user) {
        String password = Base64.getEncoder().encodeToString(user.getUsername().getBytes());
        return new User(
                user.getUsername(),
                password,
                user.getFirstName(),
                user.getLastName(),
                1,
                "",true,true
        );
    }
}
