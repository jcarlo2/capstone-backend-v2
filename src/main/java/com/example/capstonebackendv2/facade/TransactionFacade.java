package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.TransactionDTO;
import com.example.capstonebackendv2.dto.TransactionItemDTO;
import com.example.capstonebackendv2.dto.TransactionRefundDTO;
import com.example.capstonebackendv2.dto.TransactionReportDTO;
import com.example.capstonebackendv2.entity.*;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionFacade {
    private final TransactionServiceImpl service;
    private final MerchandiseServiceImpl merchandiseService;
    private final InventoryLossServiceImpl lossService;
    private final GoodsReceiptServiceImpl goodsService;
    private final DateServiceImpl dateService;
    private final GenerateReportServiceImpl generateReportService;
    private final Mapper mapper;

    public TransactionFacade(TransactionServiceImpl service, MerchandiseServiceImpl merchandiseService, InventoryLossServiceImpl lossService, GoodsReceiptServiceImpl goodsService, DateServiceImpl dateService, GenerateReportServiceImpl generateReportService, Mapper mapper) {
        this.service = service;
        this.merchandiseService = merchandiseService;
        this.lossService = lossService;
        this.goodsService = goodsService;
        this.dateService = dateService;
        this.generateReportService = generateReportService;
        this.mapper = mapper;
    }

    public String generate(String id) {
        return service.generateId(id);
    }

    public List<String> save(@NotNull TransactionDTO dto) {
        List<String> newList = new ArrayList<>();
        dto.getItemList().forEach(item -> {
            if(!merchandiseService.hasStock(item.getId(),item.getQuantity())) newList.add(item.getId());
        });
        if(newList.size() > 0) return newList;
        dto.getItemList().forEach(item -> merchandiseService.updateQuantity(item.getId(), -1 * item.getQuantity()));
        dto.getItemList().forEach(item -> merchandiseService.updateProductExpiryQuantity(item.getId(), item.getQuantity()));
        service.save(
                mapper.transactionDTOToItemEntity(dto.getItemList()),
                mapper.transactionDTOToReportEntity(dto.getReport())
        );
        return new ArrayList<>();
    }

    public List<TransactionReport> findAllReportByCriteria
            (Boolean isValid, Boolean isArchived, String search, String start, @NotNull String end, int size) {
        if(end.equals("")) end = "3023-12-31T23:59:59";
        if(start.equals("")) end = "2000-12-31T23:59:59";
        if(size < 50) size = 50;
        return service.findAllReportByCriteria(isValid,isArchived,search,start,end,size);
    }

    public List<TransactionItemDTO> findItems(String id) {
        List<TransactionReportItem> list = service.findItems(id);
        List<Merchandise> merchandiseList = new ArrayList<>();
        list.forEach(item -> merchandiseList.add(merchandiseService.findById(item.getProductId())));
        return mapper.transactionReportItemToDTO(list,merchandiseList, id);
    }

    public TransactionReportDTO findReportById(String id) {
        return mapper.transactionReportToTransactionReportDTO(service.findReportById(id));
    }

    public String forwardId(String id) {
        return service.forwardId(id);
    }

    @Transactional @Modifying
    public void returnRefund(@NotNull TransactionRefundDTO dto) {
        String lossId = lossService.generate("NP");
        String goodsId = goodsService.generate("IP");
        InventoryLossReport lossReport = mapper.refundItemsToInventoryLossReportEntity(dto,lossId);
        GoodsReceiptReport goodsReport = mapper.refundItemsToGoodsReceiptReportEntity(dto,goodsId);
        TransactionReport report = mapper.refundItemsToTransactionReportEntity(dto);
        List<TransactionReportItem> newItemList = mapper.refundItemsToTransactionItemEntity(dto.getItemList(),dto.getReport().getNewId());
        List<InventoryLossItem> lossItemList = mapper.refundItemsToInventoryLossItemEntity(dto.getItemList(),lossId);
        List<GoodsReceiptItem> goodsItemList = mapper.refundItemsToGoodsReceiptItemEntity(dto.getItemList(),goodsId);

        // save and invalidate old report
        if(lossItemList.size() > 0) lossService.saveReportAndItems(lossReport,lossItemList);
        if(goodsItemList.size() > 0) goodsService.saveReportAndItems(goodsReport,goodsItemList);

        service.save(newItemList,report);
        service.invalidate(dto.getReport().getOldId());

        // add merchandise quantity and expiration
        List<TransactionReportItem> itemList = service.findItems(dto.getReport().getOldId());
        itemList.forEach(item -> {
            merchandiseService.updateQuantity(item.getProductId(),item.getSold());
            merchandiseService.updateProductExpiryQuantityAndSetActive(item.getProductId(),item.getSold());
        });

        // deduct merchandise quantity and expiration
        dto.getItemList().forEach(item -> {
            merchandiseService.updateQuantity(item.getId(),-1 * (item.getQuantity() + item.getExpired() + item.getDamaged()));
            merchandiseService.updateProductExpiryQuantity(item.getId(),item.getQuantity() + item.getExpired() + item.getDamaged());
        });
    }

    public int countActiveReportInBetween(@NotNull String start, String option) {
        if(start.equals("")) return 0;
        start = dateService.fixStartDate(start,option);
        String end = dateService.addDays(start, option);
        return service.countActiveReportInBetween(start,end);
    }

    public List<BigDecimal> getAnnualBreakDown(String start) {
        List<BigDecimal> breakDown = new ArrayList<>();
        int MONTHS = 12;
        for(int i=1;i<=MONTHS;i++) {
            String a = start + "-"+ (i<10 ? "0" + i : i) + "-01T00:00:00";
            String b = dateService.addDays(a, "Month");
            BigDecimal total = service.getAnnualBreakDown(generateReportService.generateSalesProductReport(a,b));
            breakDown.add(total);
        }
        return breakDown;
    }
}
