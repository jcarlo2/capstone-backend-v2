package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.InventoryOption;
import com.example.capstonebackendv2.dto.LossAndGoodsReportDTO;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.GoodsReceiptServiceImpl;
import com.example.capstonebackendv2.service.impl.InventoryLossServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LossAndGoodsFacade {
    private final InventoryLossServiceImpl lossService;
    private final GoodsReceiptServiceImpl goodsService;
    private final Mapper mapper;

    public LossAndGoodsFacade(InventoryLossServiceImpl lossService, GoodsReceiptServiceImpl goodsService, Mapper mapper) {
        this.lossService = lossService;
        this.goodsService = goodsService;
        this.mapper = mapper;
    }

    public List<LossAndGoodsReportDTO> findReport(@NotNull InventoryOption option) {
        if(option.getType().equals("loss")) return mapper.inventoryLossReportListToDTO(lossService.findAllReports(option));
        return mapper.goodsReceiptReportListToDTO(goodsService.findAllReports(option));
    }
}
