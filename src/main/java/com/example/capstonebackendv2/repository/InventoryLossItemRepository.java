package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.InventoryLossItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryLossItemRepository extends CrudRepository<InventoryLossItem,String> {
    List<InventoryLossItem> findAllByReportId(String id);

    @Transactional @Modifying
    @Query(value = "DELETE FROM null_item WHERE report_id = ?1 ",nativeQuery = true)
    void delete(String id);

    List<InventoryLossItem> findAllByReportIdOrderByNum(String id);
}
