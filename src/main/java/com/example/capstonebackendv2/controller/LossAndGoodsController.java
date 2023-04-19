package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.InventoryOption;
import com.example.capstonebackendv2.dto.LossAndGoodsReportDTO;
import com.example.capstonebackendv2.facade.LossAndGoodsFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/loss-goods")
public class LossAndGoodsController {
    private final LossAndGoodsFacade facade;

    public LossAndGoodsController(LossAndGoodsFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/find-report")
    public List<LossAndGoodsReportDTO> findReport(@RequestBody InventoryOption option) {
        return facade.findReport(option);
    }
}
