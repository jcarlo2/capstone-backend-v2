package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.dto.MerchandiseDTO;
import com.example.capstonebackendv2.dto.MerchandiseDiscountDTO;
import com.example.capstonebackendv2.dto.MerchandiseHistoriesDTO;
import com.example.capstonebackendv2.dto.NotificationDTO;
import com.example.capstonebackendv2.entity.*;
import com.example.capstonebackendv2.helper.enums.Category;
import com.example.capstonebackendv2.repository.*;
import com.example.capstonebackendv2.service.MerchandiseService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        if(sortBy.equalsIgnoreCase("Stock")) sortBy = "quantity";
        Sort sort = isAscending ? Sort.by(sortBy) : Sort.by(Sort.Direction.DESC,sortBy);
        Pageable pageable = PageRequest.of(0,size, sort);
        boolean isAll = category.equals(Category.ALL);
        boolean isValidNumber = isValidPositiveNumber(search);
        List<Merchandise> list;
        if(isAll) {
            list = isValidNumber
                    ? merchandiseRepository.findAllByPriceLessThanEqualAndIsActive(new BigDecimal(search), true, pageable)
                    : merchandiseRepository.findAllByIdContainsIgnoreCaseOrDescriptionContainsIgnoreCase(search, search,pageable);
        }else {
            list = isValidNumber
                    ? merchandiseRepository.findAllByPriceLessThanEqualAndIsActive(new BigDecimal(search), true)
                    : merchandiseRepository.findAllByIdContainsIgnoreCaseOrDescriptionContainsIgnoreCase(search, search);
        }
        return isAll ? list : filterByCategory(category, sortBy, list, size, isAscending);
    }

    @Override
    public List<Merchandise> filterByCategory(Category category,String sortBy, @NotNull List<Merchandise> list, int size, boolean isAscending) {
        List<Merchandise> newList = new ArrayList<>();
        for (Merchandise merchandise : list) {
            if (size <= 0) break;
            if (categoryRepository.existsByIdAndCategory(merchandise.getId(), category)) {
                newList.add(merchandise);
            }
        }
        if(sortBy.equalsIgnoreCase("id")) newList.sort(Comparator.comparing(Merchandise::getId));
        else if(sortBy.equalsIgnoreCase("description")) newList.sort(Comparator.comparing(Merchandise::getDescription));
        else if(sortBy.equalsIgnoreCase("price")) newList.sort(Comparator.comparing(Merchandise::getPrice));
        else newList.sort(Comparator.comparing(Merchandise::getQuantity));
        if(!isAscending) Collections.reverse(newList);
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
    public void removeDiscount(@NotNull MerchandiseDiscountDTO dto, MerchandiseDiscountHistory history) {
        discountRepository.deleteAllByIdAndQuantityAndDiscount(dto.getId(), dto.getQuantity(), dto.getDiscount());
        discountHistoryRepository.save(history);
    }

    @Override
    public MerchandiseHistoriesDTO findMerchandiseHistories(String id) {
        List<MerchandiseHistory> merchandiseHistoryList = merchandiseHistoryRepository.findAllByIdOrderByCreatedAtDesc(id);
        List<MerchandiseDiscountHistory> discountHistoryList = discountHistoryRepository.findAllByIdOrderByTimestampDesc(id);
        return new MerchandiseHistoriesDTO(merchandiseHistoryList, discountHistoryList);
    }

    @Override
    public List<NotificationDTO> findNotification() {
        List<MerchandiseExpiration> expirationList = expirationRepository.findAllByIsActiveOrderByTimestampDesc("3");
        List<MerchandiseExpiration> activeList = expirationRepository.findAllByIsActiveOrderByTimestampDesc("1");
        List<Merchandise> merchandiseList = new ArrayList<>();
        merchandiseRepository.findAll().forEach(merchandiseList::add);
        List<NotificationDTO> list = new ArrayList<>();
        setListForProductNotification( expirationList, activeList, merchandiseList, list);
        return list;
    }

    private static void setListForProductNotification(
            @NotNull List<MerchandiseExpiration> expirationList,
            @NotNull List<MerchandiseExpiration> activeList,
            @NotNull List<Merchandise> merchandiseList,
            @NotNull List<NotificationDTO> list) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        int[] num = {0};
        list.addAll(expirationList.stream().map(item -> new NotificationDTO(
                num[0]++,item.getId(), item.getName(), item.getQuantity(), "Need to dispose", "dispose"
        )).toList());
        list.addAll(activeList.stream().map(item -> {
            LocalDateTime time = LocalDateTime.parse(item.getTimestamp(), formatter);
            Duration duration = Duration.between(now, time);
            long daysDiff = duration.toDays();
            String reason = "";
            String color = "critical";

            if (daysDiff <= 1) reason = "Will expired today";
            else if (daysDiff <= 2) reason = "Expiration within 2 days";
            else if (daysDiff <= 3) reason = "Expiration within 3 days";
            else if (daysDiff <= 7) {
                reason = "Expiration within a week";
                color = "low";
            } else if (daysDiff <= 14) {
                reason = "Expiration within two weeks";
                color = "low";
            }
            if (reason.equals("")) return null;
            return new NotificationDTO(num[0]++,item.getId(), item.getName(), item.getQuantity(), reason, color);
        }).filter(Objects::nonNull).toList());
        list.addAll(merchandiseList.stream().map(item -> {
            String reason = "";
            String color = "low";
            if (item.getQuantity() == 0 || item.getQuantity() <= item.getPiecesPerBox() / 2) {
                reason = "Critical Stock";
                color = "critical";
            } else if (item.getQuantity() <= item.getPiecesPerBox()) reason = "Low Stock";
            if (reason.equals("")) return null;
            return new NotificationDTO(num[0]++,item.getId(), item.getDescription(), item.getQuantity(), reason, color);
        }).filter(Objects::nonNull).toList());
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

    @Override @Transactional
    public void updateQuantity(String id, Integer quantity) {
        merchandiseRepository.updateQuantity(id,quantity);
    }

    @Override @Transactional
    public void updateProductExpiryQuantity(String id, Integer quantity) {
        List<MerchandiseExpiration> list = expirationRepository.findAllByIdAndIsActiveOrderByTimestamp(id, "1");
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
        MerchandiseExpiration merchandise = expirationRepository.findByIdAndReportIdAndIsActiveOrderByTimestampDesc(id,reportId,"1");
        return merchandise != null ? merchandise.getTimestamp() : "";
    }

    @Override
    public void updateMerchandisePrice(String id, BigDecimal price) {
        merchandiseRepository.updatePrice(id,price);
    }

    @Override
    public boolean isMerchandiseExpirationActiveAndHasCompleteStock(String id, String reportId, Integer quantity) {
        return expirationRepository.existsByIdAndReportIdAndIsActiveAndQuantityLessThan(id,reportId,"1",quantity);
    }

    @Override
    public void invalidateExpiration(String id, String reportId) {
        expirationRepository.setInactive(id,reportId);
    }

    @Override @Transactional
    public void updateProductExpiryQuantityAndSetActive(String id, Integer quantity) {
        List<MerchandiseExpiration> expirationList = expirationRepository.findAllByIdAndIsActiveOrderByTimestamp(id,"1");
        if(expirationList.size() == 0) expirationList = expirationRepository.findAllByIdAndIsActiveOrderByTimestampDesc(id,"0");
        MerchandiseExpiration expiration = expirationList.get(0);
        quantity = expiration.getQuantity() < 0 ? quantity : expiration.getQuantity() + quantity;
        expirationRepository.updateQuantityAndSetToActive(expiration.getId(),quantity);
    }

    @Override
    public List<MerchandiseDiscount> findAllDiscount(String id) {
        return discountRepository.findAllByIdAndIsValidOrderByDiscount(id,true);
    }

    /**
     * Return a list of MerchandiseExpiration where timestamp is greater than date today
     * **/
    @Override
    public List<MerchandiseExpiration> autoCheckMerchandiseExpiration() {
        List<MerchandiseExpiration> list = new ArrayList<>();
        Iterable<Merchandise> merchandiseList = merchandiseRepository.findAll();
        String dateToday = LocalDate.now() + " 23:59:59";
        for(Merchandise merch : merchandiseList) {
            List<MerchandiseExpiration> merchandiseExpirationList = getAllActiveExpirationById(merch.getId());
            if(merchandiseExpirationList != null) {
                merchandiseExpirationList.forEach(merchandiseExpiration -> {
                    if(compareByDate(dateToday,merchandiseExpiration.getTimestamp())) {
                        list.add(merchandiseExpiration);
                    }
                });
            }
        }
        return list;
    }

    @Override
    public void dispose(String id) {
        List<MerchandiseExpiration> expirationList = expirationRepository.findAllByIdAndIsActiveOrderByTimestampDesc(id,"3");
        expirationList.forEach(item -> {
            expirationRepository.dispose(item.getId(),item.getTimestamp(),item.getReportId());
            merchandiseRepository.updateQuantity(item.getId(),-1 * item.getQuantity());
        });
    }

    @Override
    public void updateToDisposeExpiration(String id, String timestamp, String reportId) {
        expirationRepository.updateToDisposeExpiration(id,timestamp, reportId);
    }

    @Override
    public @Nullable List<MerchandiseExpiration> getAllActiveExpirationById(String id) {
        List<MerchandiseExpiration> merch = expirationRepository.findAllByIdAndIsActiveOrderByTimestamp(id,"1");
        return merch.size() > 0 ? merch : null;
    }

    @Override
    public String generateId(@NotNull String id) {
        if(id.equals("")) id = generate();
        while(merchandiseRepository.existsByIdIgnoreCase(id)) id = generate();
        return id;
    }
}
