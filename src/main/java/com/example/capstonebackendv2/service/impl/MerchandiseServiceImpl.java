package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.dto.MerchandiseDTO;
import com.example.capstonebackendv2.entity.*;
import com.example.capstonebackendv2.helper.enums.Category;
import com.example.capstonebackendv2.repository.*;
import com.example.capstonebackendv2.service.MerchandiseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MerchandiseServiceImpl implements MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandiseExpirationRepository expirationRepository;
    private final MerchandiseDiscountRepository discountRepository;
    private final MerchandiseDiscountHistoryRepository discountHistoryRepository;
    private final MerchandiseHistoryRepository merchandiseHistoryRepository;
    private final MerchandiseCategoryRepository categoryRepository;

    public MerchandiseServiceImpl(MerchandiseRepository merchandiseRepository, MerchandiseExpirationRepository expirationRepository, MerchandiseDiscountRepository discountRepository, MerchandiseDiscountHistoryRepository discountHistoryRepository, MerchandiseHistoryRepository merchandiseHistoryRepository, MerchandiseCategoryRepository categoryRepository) {
        this.merchandiseRepository = merchandiseRepository;
        this.expirationRepository = expirationRepository;
        this.discountRepository = discountRepository;
        this.discountHistoryRepository = discountHistoryRepository;
        this.merchandiseHistoryRepository = merchandiseHistoryRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Merchandise> findAll(int size, String sortBy, Category category, String search, boolean isAscending) {
        if(size < 50) size = 50;
        if(sortBy.equals("Stock")) sortBy = "quantity";
        Sort sort = isAscending ? Sort.by(sortBy) : Sort.by(Sort.Direction.DESC,sortBy);
        Pageable pageable = PageRequest.of(0,size, sort);
        Page<Merchandise> pageList = isValidPositiveNumber(search)
            ? merchandiseRepository.findAllByPriceLessThanEqualAndIsActive(new BigDecimal(search), true, pageable)
            : merchandiseRepository.findAllByIdContainsIgnoreCaseOrDescriptionContainsIgnoreCase(search, search,pageable);

        return filterByCategory(category, pageList.getContent());
    }

    @Override
    public List<Merchandise> filterByCategory(Category category, @NotNull List<Merchandise> list) {
        if(category == Category.ALL) return list;
        List<Merchandise> newList = new ArrayList<>();
        for (Merchandise item : list) {
            if (categoryRepository.existsByIdAndCategory(item.getId(), category)) {
                newList.add(item);
            }
        }
        return newList;
    }

    @Override
    public boolean isValidPositiveNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        String regex = "\\d+(\\.\\d+)?";
        return str.matches(regex) && Double.parseDouble(str) > 0;
    }

    @Override
    public MerchandiseHistory createHistory(@NotNull Merchandise original, @NotNull MerchandiseDTO dto) {
        boolean isMarkUp = dto.getPrice().compareTo(original.getPrice()) >= 0;
        BigDecimal markPrice = dto.getPrice().subtract(original.getPrice());

        return new MerchandiseHistory(
                "",
                dto.getId(),
                dto.getDescription(),
                dto.getPrice(),
                markPrice.abs(),
                0.0,
                isMarkUp,""
        );
    }

    @Override
    public void updateInfo(MerchandiseHistory history, Merchandise merchandise) {
        merchandiseRepository.save(merchandise);
        merchandiseHistoryRepository.save(history);
    }

    @Override
    public List<String> findAllCategory(String id) {
        List<MerchandiseCategory> categoryList = categoryRepository.findAllByIdOrderByCategory(id);
        return categoryList
                .stream()
                .map(category -> category.getCategory().name())
                .collect(Collectors.toList());
    }

    @Override
    public void saveAllCategory(@NotNull List<MerchandiseCategory> list) {
        categoryRepository.deleteAllCategoryById(list.get(0).getId());
        if(!list.get(0).getCategory().equals(Category.NONE)) categoryRepository.saveAll(list);

    }

    @Override
    public boolean isDiscountExist(String id, Integer quantity) {
        return discountRepository.existsByIdAndQuantity(id,quantity);
    }

    @Override
    public void saveDiscount(MerchandiseDiscount discount) {
        discountRepository.save(discount);
        discountHistoryRepository.save(new MerchandiseDiscountHistory(
                "",
                discount.getId(),
                discount.getDiscount(),
                discount.getQuantity(),
                ""
        ));
    }

    @Override
    public List<MerchandiseHistory> findMerchandiseHistory(String id) {
        return merchandiseHistoryRepository.findAllByIdOrderByCreatedAtDesc(id);
    }

    @Override
    public boolean isMerchandiseExist(String id) {
        return merchandiseRepository.existsByIdIgnoreCase(id);
    }

    @Override
    public void addProduct(List<MerchandiseCategory> categoryList, Merchandise merchandise) {
        merchandiseRepository.save(merchandise);
        categoryRepository.saveAll(categoryList);
    }

    @Override
    public Merchandise findById(String id) {
        return merchandiseRepository.findById(id).orElse(new Merchandise(
                "","", BigDecimal.ZERO,0,0,0.0,
                true,0.0,0.0,true));
    }

    @Override
    public boolean hasStock(String id, Integer quantity) {
        Merchandise merchandise = merchandiseRepository.findByIdAndIsActive(id,true);
        return merchandise.getQuantity() >= quantity;
    }

    @Override
    public double getDiscount(String id, Integer quantity) {
        MerchandiseDiscount discount = discountRepository.getDiscount(id,quantity);
        return discount == null ? 0 : discount.getDiscount();
    }

    @Override
    public void updateQuantity(String id, Integer quantity) {
        merchandiseRepository.updateQuantity(id,quantity);
    }

    @Override @Transactional
    public void updateProductExpiryQuantity(String id, Integer quantity) {
        List<MerchandiseExpiration> list = expirationRepository.findAllByIdAndIsActiveOrderByTimestamp(id, true);
        if (list.isEmpty()) return;
        for (MerchandiseExpiration expiration : list) {
            int remainingQuantity = quantity - expiration.getQuantity();
            if (remainingQuantity <= 0) {
                expirationRepository.updateQuantity(id, -1 * quantity, expiration.getTimestamp());
                if (expiration.getQuantity().equals(quantity)) expirationRepository.toInactiveWithTimestamp(id, expiration.getTimestamp(), expiration.getReportId());
                break;
            } else {
                expirationRepository.updateQuantity(id, -1 * expiration.getQuantity(), expiration.getTimestamp());
                expirationRepository.toInactiveWithTimestamp(id, expiration.getTimestamp(), expiration.getReportId());
                quantity = remainingQuantity;
            }
        }
    }

    @Override
    public void saveProductExpiration(List<MerchandiseExpiration> itemList) {
        expirationRepository.saveAll(itemList);
    }

    @Override
    public String findExpirationDateById(String id, String reportId) {
        MerchandiseExpiration merchandise = expirationRepository.findByIdAndReportIdAndIsActiveOrderByTimestampDesc(id,reportId,true);
        return merchandise != null ? merchandise.getTimestamp() : "";
    }

    @Override
    public void updateMerchandisePrice(String id, BigDecimal price) {
        merchandiseRepository.updatePrice(id,price);
    }

    @Override
    public boolean isMerchandiseExpirationActiveAndHasStock(String id, String reportId, Integer quantity) {
        return expirationRepository.existsByIdAndReportIdAndIsActiveAndQuantityGreaterThan(id,reportId,true,quantity);
    }

    @Override
    public void invalidateExpiration(String id, String reportId) {
        expirationRepository.setInactive(id,reportId);
    }

    @Override @Transactional
    public void updateProductExpiryQuantityAndSetActive(String id, Integer quantity) {
        List<MerchandiseExpiration> expirationList = expirationRepository.findAllByIdAndIsActiveOrderByTimestamp(id,true);
        if(expirationList.size() == 0) expirationList = expirationRepository.findAllByIdAndIsActiveOrderByTimestampDesc(id,false);
        MerchandiseExpiration expiration = expirationList.get(0);
        quantity = expiration.getQuantity() < 0 ? quantity : expiration.getQuantity() + quantity;
        expirationRepository.updateQuantityAndSetToActive(expiration.getId(),quantity);
    }

    @Override
    public List<MerchandiseDiscount> findAllDiscount(String id) {
        return discountRepository.findAllByIdAndIsValidOrderByDiscount(id,true);
    }
}
