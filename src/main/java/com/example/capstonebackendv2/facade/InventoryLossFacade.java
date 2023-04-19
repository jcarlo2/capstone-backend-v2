package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.InventoryLossDTO;
import com.example.capstonebackendv2.dto.InventoryLossItemDTO;
import com.example.capstonebackendv2.dto.InventoryLossReportDTO;
import com.example.capstonebackendv2.entity.InventoryLossItem;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.InventoryLossServiceImpl;
import com.example.capstonebackendv2.service.impl.MerchandiseServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryLossFacade {
    private final InventoryLossServiceImpl service;
    private final MerchandiseServiceImpl merchandiseService;
    private final Mapper mapper;

    public InventoryLossFacade(InventoryLossServiceImpl service, MerchandiseServiceImpl merchandiseService, Mapper mapper) {
        this.service = service;
        this.merchandiseService = merchandiseService;
        this.mapper = mapper;
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
        itemList.forEach(item -> {
            merchandiseService.updateQuantity(item.getId(), item.getQuantity());
            merchandiseService.updateProductExpiryQuantityAndSetActive(item.getId(),item.getQuantity());
        });
        return service.invalidate(id);
    }
}
