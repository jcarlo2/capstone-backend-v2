package com.example.capstonebackendv2.controller;

import com.example.capstonebackendv2.dto.*;
import com.example.capstonebackendv2.facade.MerchandiseFacade;
import com.example.capstonebackendv2.helper.enums.Category;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/merchandise")
public class MerchandiseController {

    private final MerchandiseFacade facade;

    public MerchandiseController(MerchandiseFacade facade) {
        this.facade = facade;
    }

    @GetMapping("/find-all")
    public List<MerchandiseDTO> findAll(
            @RequestParam(defaultValue = "50") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ALL") String filterBy,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "true") boolean isAscending
    ) {
        Category category = Category.fromString(filterBy);
        return facade.findAll(size, sortBy, category, search, isAscending);
    }

    @GetMapping("/find-by-id")
    public MerchandiseDTO findById(@RequestParam String id) {
        return facade.findById(id);
    }

    @GetMapping("/product-exist")
    public boolean isMerchandiseExist(@RequestParam String id) {
        return facade.isMerchandiseExist(id);
    }

    @GetMapping("/check-stock")
    public boolean hasStock(@RequestParam String id,@RequestParam Integer quantity) {
        return facade.hasStock(id,quantity);
    }

    @GetMapping("/get-discount")
    public Double getDiscount(@RequestParam String id,@RequestParam Integer quantity) {
        return facade.getDiscount(id,quantity);
    }

    @GetMapping("/find-all-discount")
    public List<MerchandiseDiscountDTO> findAllDiscount(@RequestParam String id) {
        return facade.findAllDiscount(id);
    }

    @GetMapping("/find-all-category")
    public List<String> findAllCategory(@RequestParam String id) {
        return facade.findAllCategory(id);
    }

    @GetMapping("/discount-exist")
    public boolean isDiscountExist(@RequestParam String id, @RequestParam Integer quantity) {
        return facade.isDiscountExist(id,quantity);
    }

    @GetMapping("/find-product-history")
    public List<MerchandiseHistoryDTO> findMerchandiseHistory(@RequestParam String id) {
        return facade.findMerchandiseHistory(id);
    }

    @MessageMapping("/save-discount")
    @SendTo("/topic/merchandise/save-discount-listener")
    @PostMapping("save-discount")
    public String saveDiscount(@RequestBody MerchandiseDiscountDTO discount) {
        facade.saveDiscount(discount);
        return discount.getId();
    }

    @PostMapping("/save-all-category")
    public void saveAllCategory(@RequestBody List<MerchandiseCategoryDTO> categoryList) {
        facade.saveAllCategory(categoryList);
    }

    @PostMapping("add-product")
    public void addProduct(@RequestBody MerchandiseAddDTO dto) {
        facade.addProduct(dto);
    }

    @MessageMapping("/update-info-sender")
    @SendTo("/topic/merchandise/update-info-listener")
    @PostMapping("/update-info")
    public String updateInfo(@RequestBody MerchandiseDTO dto) {
        facade.updateInfo(dto);
        return dto.getId();
    }
}
