package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.GoodsReceiptItemDTO;
import com.example.capstonebackendv2.dto.GoodsReceiptReportDTO;
import com.example.capstonebackendv2.entity.GoodsReceiptItem;
import com.example.capstonebackendv2.entity.GoodsReceiptReport;
import com.example.capstonebackendv2.gui.GUI;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.GoodsReceiptServiceImpl;
import com.example.capstonebackendv2.service.impl.MerchandiseServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class GoodsReceiptFacade {
    private final GoodsReceiptServiceImpl service;
    private final MerchandiseServiceImpl merchandiseService;
    private final Mapper mapper;
    private final GUI gui;

    public GoodsReceiptFacade(GoodsReceiptServiceImpl service, MerchandiseServiceImpl merchandiseService, Mapper mapper, GUI gui) {
        this.service = service;
        this.merchandiseService = merchandiseService;
        this.mapper = mapper;
        this.gui = gui;
        autoArchivedRecordFromPastYear();
    }

    private void autoArchivedRecordFromPastYear() {
        Runnable run = () -> {
            LocalDate pastDate = LocalDate.now();
            int num = gui.getArchiveNumber();
            switch (num) {
                case 1 -> pastDate = pastDate.minusMonths(1);
                case 3 -> pastDate = pastDate.minusMonths(3);
                case 6 -> pastDate = pastDate.minusMonths(6);
                default -> pastDate = pastDate.minusYears(1);
            }
            String date = pastDate + "T23:59:59";
            List<GoodsReceiptReport> reportList = service.findAllValidReportByEnd(date);
            reportList.forEach(report -> service.archive(report.getId()));
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(run,1,60, TimeUnit.SECONDS);
    }

    public String generateId(String id) {
        return service.generateId(id);
    }

    public void saveReportAndItems(List<GoodsReceiptItemDTO> list, GoodsReceiptReportDTO reportDTO) {
        List<GoodsReceiptItem> items = mapper.goodsReceiptDTOToItemEntity(list);
        GoodsReceiptReport report = mapper.goodsReceiptDTOToReportEntity(reportDTO);
        list.forEach(product -> {
            merchandiseService.updateQuantity(product.getId(), product.getQuantity());
            merchandiseService.updateMerchandisePrice(product.getId(),product.getPrice().add(product.getMarkPrice()));
        });
        merchandiseService.saveProductExpiration(mapper.goodsReceiptItemDTOToExpirationEntity(list));
        service.saveReportAndItems(report,items);
    }

    public List<GoodsReceiptItemDTO> findAllItems(String id) {
        List<GoodsReceiptItem> itemList = service.findAllItems(id);
        List<GoodsReceiptItemDTO> itemDTO = mapper.goodsReceiptItemEntityToDTO(itemList);
        itemDTO.forEach(item -> {
            String expiration = merchandiseService.findExpirationDateById(item.getId(),item.getReportId());
            item.setExpiration(expiration);
        });
        return itemDTO;
    }

    public GoodsReceiptReportDTO findReportById(String id) {
        return mapper.goodsReceiptReportToDTO(service.findReportById(id));
    }

    public boolean invalidate(String id) {
        List<GoodsReceiptItem> itemList = service.findAllItems(id);
        for(GoodsReceiptItem item : itemList) {
            if(
                !merchandiseService.hasStock(item.getProductId(), item.getQuantity()) ||
                merchandiseService.isMerchandiseExpirationActiveAndHasCompleteStock(item.getProductId(),id, item.getQuantity())
            ) return false;
        }
        itemList.forEach(item -> {
            merchandiseService.updateQuantity(item.getProductId(),-1 * item.getQuantity());
            merchandiseService.invalidateExpiration(item.getProductId(),id);
        });
        service.invalidate(id);
        return true;
    }
}
