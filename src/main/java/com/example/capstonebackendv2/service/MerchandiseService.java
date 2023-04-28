package com.example.capstonebackendv2.service;

import com.example.capstonebackendv2.dto.MerchandiseDTO;
import com.example.capstonebackendv2.dto.MerchandiseDiscountDTO;
import com.example.capstonebackendv2.dto.MerchandiseHistoriesDTO;
import com.example.capstonebackendv2.dto.NotificationDTO;
import com.example.capstonebackendv2.entity.*;
import com.example.capstonebackendv2.helper.enums.Category;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
    boolean isMerchandiseExpirationActiveAndHasCompleteStock(String id, String reportId, Integer quantity);
    void invalidateExpiration(String id, String report);
    void updateProductExpiryQuantityAndSetActive(String id, Integer quantity);
    List<MerchandiseDiscount> findAllDiscount(String id);
    List<Merchandise> filterByCategory(Category category, String sortBy, @NotNull List<Merchandise> pages, int size, boolean isAscending);
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
    void removeDiscount(MerchandiseDiscountDTO dto, MerchandiseDiscountHistory history);
    MerchandiseHistoriesDTO findMerchandiseHistories(String id);
    List<NotificationDTO> findNotification();
    List<MerchandiseExpiration> getAllActiveExpirationById(String id);
    void updateToDisposeExpiration(String id, String timestamp, String reportId);
    List<MerchandiseExpiration> autoCheckMerchandiseExpiration();
    void dispose(String id);
    String generateId(String id);
    default boolean compareByDate(String a, String b) {
        try {
            SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
            Date dateA = dtf.parse(a);
            Date dateB = dtf.parse(b);
            if(dateA.compareTo(dateB) > 0) return true;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    default String generate() {
        Random random = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        return letters.charAt(random.nextInt(26)) + ""
                + letters.charAt(random.nextInt(26)) + ""
                + letters.charAt(random.nextInt(26))
                + String.format("%06d", random.nextInt(1000000));
    }
}
