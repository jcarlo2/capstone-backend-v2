package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.GoodsReceiptItem;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GoodsReceiptItemRepository extends CrudRepository<GoodsReceiptItem,String> {
    List<GoodsReceiptItem> findAllByUniqueId(String id);

    @Transactional @Modifying
    @Query(value = "DELETE FROM product_item WHERE unique_id = ?1 ",nativeQuery = true)
    void delete(String id);

    List<GoodsReceiptItem> findAllByUniqueIdOrderByNum(String id);
}
