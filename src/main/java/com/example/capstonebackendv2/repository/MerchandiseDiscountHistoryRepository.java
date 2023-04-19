package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.MerchandiseDiscountHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchandiseDiscountHistoryRepository extends CrudRepository<MerchandiseDiscountHistory, String> {
    List<MerchandiseDiscountHistory> findAllByIdOrderByTimestampDesc(String id);
}
