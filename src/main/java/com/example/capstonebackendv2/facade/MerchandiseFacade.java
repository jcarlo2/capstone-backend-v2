package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.*;
import com.example.capstonebackendv2.entity.Merchandise;
import com.example.capstonebackendv2.entity.MerchandiseCategory;
import com.example.capstonebackendv2.entity.MerchandiseHistory;
import com.example.capstonebackendv2.helper.enums.Category;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.service.impl.MerchandiseServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchandiseFacade {
    private final MerchandiseServiceImpl service;
    private final Mapper mapper;

    public MerchandiseFacade(MerchandiseServiceImpl service, Mapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public List<MerchandiseDTO> findAll(int size, String sortBy, Category category, String search, boolean isAscending) {
        List<Merchandise> list = service.findAll(size,sortBy,category,search, isAscending);
        return mapper.merchandiseEntityToDTO(list);
    }

    public MerchandiseDTO findById(String id) {
        Merchandise merchandise = service.findById(id);
        return mapper.merchandiseEntityToDTO(merchandise);
    }

    public boolean hasStock(String id, Integer quantity) {
        return service.hasStock(id,quantity);
    }

    public Double getDiscount(String id, Integer quantity) {
        return service.getDiscount(id,quantity);
    }

    public List<MerchandiseDiscountDTO> findAllDiscount(String id) {
        return mapper.discountEntityToDTO(service.findAllDiscount(id));
    }

    public void updateInfo(@NotNull MerchandiseDTO dto) {
        Merchandise original = service.findById(dto.getId());
        MerchandiseHistory history = service.createHistory(original,dto);
        Merchandise merchandise = mapper.merchandiseDTOToEntity(dto);
        service.updateInfo(history,merchandise);
    }

    public List<String> findAllCategory(String id) {
        return service.findAllCategory(id);
    }

    public void saveAllCategory(List<MerchandiseCategoryDTO> categoryList) {
        service.saveAllCategory(mapper.merchandiseCategoryDTOToEntity(categoryList));
    }

    public boolean isDiscountExist(String id, Integer quantity) {
        return service.isDiscountExist(id,quantity);
    }

    public void saveDiscount(MerchandiseDiscountDTO discount) {
        service.saveDiscount(mapper.discountDTOToEntity(discount));
    }

    public List<MerchandiseHistoryDTO> findMerchandiseHistory(String id) {
        return mapper.merchandiseHistoryEntityToDTO(service.findMerchandiseHistory(id));
    }

    public boolean isMerchandiseExist(String id) {
        return service.isMerchandiseExist(id);
    }

    public void addProduct(@NotNull MerchandiseAddDTO dto) {
        List<MerchandiseCategory> categoryList = mapper.addProductCategoryListToEntity(dto.getCategoryList(),dto.getMerchandise().getId());
        Merchandise merchandise = mapper.merchandiseDTOToEntity(dto.getMerchandise());
        service.addProduct(categoryList, merchandise);
    }
}
