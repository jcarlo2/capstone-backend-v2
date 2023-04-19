package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.GoodsReceiptDTO;
import com.example.capstonebackendv2.dto.GoodsReceiptItemDTO;
import com.example.capstonebackendv2.dto.GoodsReceiptReportDTO;
import com.example.capstonebackendv2.facade.GoodsReceiptFacade;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/goods")
public class GoodsReceiptController {
    private final GoodsReceiptFacade facade;

    public GoodsReceiptController(GoodsReceiptFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/generate")
    public String generateId(@RequestParam String id) {
        return facade.generateId(id);
    }

    @GetMapping("/find-all-items")
    public List<GoodsReceiptItemDTO> findAllItems(@RequestParam String id) {
        return facade.findAllItems(id);
    }

    @GetMapping("/find-report")
    public GoodsReceiptReportDTO findReportById(@RequestParam String id) {
        return facade.findReportById(id);
    }

    @PostMapping("/save")
    public void save(@RequestBody @NotNull GoodsReceiptDTO dto) {
        facade.saveReportAndItems(dto.getItemList(),dto.getReport());
    }

    @GetMapping("/invalidate")
    public boolean invalidate(@RequestParam String id) {
        return facade.invalidate(id);
    }
}
