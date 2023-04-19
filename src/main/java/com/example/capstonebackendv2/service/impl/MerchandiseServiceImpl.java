package com.example.capstonebackendv2.service.impl;

import com.example.capstonebackendv2.entity.Merchandise;
import com.example.capstonebackendv2.entity.MerchandiseDiscount;
import com.example.capstonebackendv2.helper.enums.Category;
import com.example.capstonebackendv2.repository.MerchandiseCategory;
import com.example.capstonebackendv2.repository.MerchandiseDiscountRepository;
import com.example.capstonebackendv2.repository.MerchandiseRepository;
import com.example.capstonebackendv2.service.MerchandiseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MerchandiseServicesImpl implements MerchandiseService {
    private final MerchandiseRepository merchandiseRepository;
    private final MerchandiseDiscountRepository discountRepository;
    private final MerchandiseCategory categoryRepository;

    public MerchandiseServicesImpl(MerchandiseRepository merchandiseRepository, MerchandiseDiscountRepository discountRepository, MerchandiseCategory categoryRepository) {
        this.merchandiseRepository = merchandiseRepository;
        this.discountRepository = discountRepository;
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

    public static boolean isValidPositiveNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        String regex = "\\d+(\\.\\d+)?";
        return str.matches(regex) && Double.parseDouble(str) > 0;
    }

    @Override
    public Merchandise findById(String id) {
        return merchandiseRepository.findById(id).orElse(new Merchandise(
                "","", BigDecimal.ZERO,0,0,0.0,
                true,0.0,0.0,""));
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
}
