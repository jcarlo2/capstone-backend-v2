package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.GoodsReceiptReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GoodsReceiptReportRepository extends CrudRepository<GoodsReceiptReport,String> {
    List<GoodsReceiptReport> findAllByIsValidAndIsArchivedAndIdContainingIgnoreCaseAndTimestampGreaterThanEqualAndTimestampLessThanEqualAndReasonContainingIgnoreCaseOrderByTimestampDesc
            (Boolean isValid, Boolean isArchived, String search, String start, String end, String reason, Pageable pageable);

    @Transactional @Modifying
    @Query(value = "UPDATE product_report SET is_valid = 0, is_archived = 1 WHERE id = ?1", nativeQuery = true)
    void setInactive(String id);

    List<GoodsReceiptReport> findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(Boolean isValid, String start, String end);

    List<GoodsReceiptReport> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String end);

    @Modifying @Transactional
    @Query(value = "UPDATE product_report SET is_archived = 1 WHERE id = ?1",nativeQuery = true)
    void archive(String id);
}
