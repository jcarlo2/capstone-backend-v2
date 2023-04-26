package com.example.capstonebackendv2.facade;

import com.example.capstonebackendv2.dto.*;
import com.example.capstonebackendv2.entity.*;
import com.example.capstonebackendv2.helper.Mapper;
import com.example.capstonebackendv2.helper.enums.Category;
import com.example.capstonebackendv2.service.impl.MerchandiseServiceImpl;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MerchandiseFacade {
    private final MerchandiseServiceImpl service;
    private final Mapper mapper;

    public MerchandiseFacade(MerchandiseServiceImpl service, Mapper mapper) {
        this.service = service;
        this.mapper = mapper;
        autoCheckerOfMerchandiseExpiration();
    }

    /**
     * Auto checking and validating of all merchandise expiration
     * set isActive to false then create @NullReport
     **/
    private void autoCheckerOfMerchandiseExpiration() {
        Runnable run = () -> {
            List<MerchandiseExpiration> merchandiseExpirationList = service.autoCheckMerchandiseExpiration();
            merchandiseExpirationList.forEach(item -> service.updateToDisposeExpiration(
                    item.getId(),
                    item.getTimestamp(),
                    item.getReportId()
            ));
        };
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.scheduleAtFixedRate(run,1,60, TimeUnit.SECONDS);
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

    public void removeDiscount(MerchandiseDiscountDTO dto) {
        MerchandiseDiscountHistory history = mapper.merchandiseDTOToHistoryEntity(dto);
        service.removeDiscount(dto,history);
    }

    public MerchandiseHistoriesDTO findMerchandiseHistories(String id) {
        return service.findMerchandiseHistories(id);
    }

    public List<NotificationDTO> findNotification() {
        return service.findNotification();
    }

    public void dispose(String id) {
        service.dispose(id);
    }

    public String generateId(String id) {
        return service.generateId(id);
    }
}
