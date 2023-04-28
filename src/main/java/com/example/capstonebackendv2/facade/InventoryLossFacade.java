package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.InventoryLossDTO;
import com.example.capstonebackendv2.dto.InventoryLossItemDTO;
import com.example.capstonebackendv2.dto.InventoryLossReportDTO;
import com.example.capstonebackendv2.entity.InventoryLossItem;
import com.example.capstonebackendv2.entity.InventoryLossReport;
import com.example.capstonebackendv2.gui.GUI;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.InventoryLossServiceImpl;
import com.example.capstonebackendv2.service.impl.MerchandiseServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class InventoryLossFacade {
    private final InventoryLossServiceImpl service;
    private final MerchandiseServiceImpl merchandiseService;
    private final Mapper mapper;
    private final GUI gui;

    public InventoryLossFacade(InventoryLossServiceImpl service, MerchandiseServiceImpl merchandiseService, Mapper mapper, GUI gui) {
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
            List<InventoryLossReport> reportList = service.findAllValidReportByEnd(date);
            reportList.forEach(report -> service.archive(report.getId()));
        };
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(run,1,60, TimeUnit.SECONDS);
    }

    public String generate(String id) {
        return service.generateId(id);
    }

    public List<String> save(@NotNull InventoryLossDTO dto) {
        List<String> stringList = new ArrayList<>();
        dto.getItemList().forEach(item -> {
            if(!merchandiseService.hasStock(item.getId(),item.getQuantity())) {
                stringList.add(item.getId());
            }
        });

        if(stringList.size() > 0) return stringList;
        service.saveReportAndItems(
                mapper.inventoryLossDTOToReportEntity(dto.getReport()),
                mapper.inventoryLossDTOToItemEntity(dto.getItemList())
        );
        dto.getItemList().forEach(item -> {
            merchandiseService.updateQuantity(item.getId(),-1 * item.getQuantity());
            merchandiseService.updateProductExpiryQuantity(item.getId(),item.getQuantity());
        });
        return new ArrayList<>();
    }

    public List<InventoryLossItemDTO> findAllItems(String id) {
        return mapper.inventoryLossItemEntityToDTO(service.findAllItems(id));
    }

    public InventoryLossReportDTO findReportById(String id) {
        return mapper.inventoryLossReportToDTO(service.findReportById(id));
    }

    public boolean invalidate(String id) {
        List<InventoryLossItem> itemList = service.findAllItems(id);
        System.out.println(itemList.size());
        itemList.forEach(item -> {
            merchandiseService.updateQuantity(item.getId(), item.getQuantity());
            merchandiseService.updateProductExpiryQuantityAndSetActive(item.getId(),item.getQuantity());
        });
        return service.invalidate(id);
    }
}
