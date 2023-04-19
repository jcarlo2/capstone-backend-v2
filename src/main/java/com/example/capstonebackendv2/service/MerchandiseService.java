package com.example.capstonebackendv2.service;

import com.example.capstonebackendv2.dto.MerchandiseDTO;
import com.example.capstonebackendv2.entity.*;
import com.example.capstonebackendv2.helper.enums.Category;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.List;

public interface MerchandiseService {
    List<Merchandise> findAll(int size, String sortBy, Category filterBy, String search, boolean isAscending);
    Merchandise findById(String id);
    boolean hasStock(String id, Integer quantity);
    double getDiscount(String id, Integer quantity);
    void updateQuantity(String id, Integer quantity);
    void updateProductExpiryQuantity(String id, Integer quantity);
    void saveProductExpiration(List<MerchandiseExpiration> itemList);
    String findExpirationDateById(String id, String reportId);
    void updateMerchandisePrice(String id, BigDecimal price);
    boolean isMerchandiseExpirationActiveAndHasStock(String id, String reportId, Integer quantity);
    void invalidateExpiration(String id, String report);
    void updateProductExpiryQuantityAndSetActive(String id, Integer quantity);
    List<MerchandiseDiscount> findAllDiscount(String id);
    List<Merchandise> filterByCategory(Category category, @NotNull List<Merchandise> list);
    boolean isValidPositiveNumber(String str);
    MerchandiseHistory createHistory(Merchandise original, MerchandiseDTO dto);
    void updateInfo(MerchandiseHistory history, Merchandise merchandise);
    List<String> findAllCategory(String id);
    void saveAllCategory(List<MerchandiseCategory> merchandiseCategoryDTOToEntity);
    boolean isDiscountExist(String id, Integer quantity);
    void saveDiscount(MerchandiseDiscount discount);
    List<MerchandiseHistory> findMerchandiseHistory(String id);
    boolean isMerchandiseExist(String id);
    void addProduct(List<MerchandiseCategory> categoryList, Merchandise merchandise);
}
