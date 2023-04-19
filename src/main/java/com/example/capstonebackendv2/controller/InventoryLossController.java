package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.GoodsReceiptReportDTO;
import com.example.capstonebackendv2.dto.InventoryLossDTO;
import com.example.capstonebackendv2.dto.InventoryLossItemDTO;
import com.example.capstonebackendv2.dto.InventoryLossReportDTO;
import com.example.capstonebackendv2.facade.InventoryLossFacade;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/loss")
public class InventoryLossController {
    private final InventoryLossFacade facade;

    public InventoryLossController(InventoryLossFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/generate")
    public String generate(@RequestParam String id) {
        return facade.generate(id);
    }

    @PostMapping("/save")
    public List<String> save(@RequestBody InventoryLossDTO dto) {
        return facade.save(dto);
    }

    @GetMapping("/find-all-items")
    public List<InventoryLossItemDTO> findAllItems(@RequestParam String id) {
        return facade.findAllItems(id);
    }

    @GetMapping("/find-report")
    public InventoryLossReportDTO findReportById(@RequestParam String id) {
        return facade.findReportById(id);
    }

    @GetMapping("/invalidate")
    public boolean invalidate(@RequestParam String id) {
        return facade.invalidate(id);
    }
}
