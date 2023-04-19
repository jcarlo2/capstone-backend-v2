package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.GoodsReceiptReport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DeliveryReportRepository extends CrudRepository<GoodsReceiptReport,String> {
    @Transactional @Modifying
    @Query(value = "UPDATE product_report SET is_archived = 1 WHERE id = ?1",nativeQuery = true)
    void archive(String id);

    @Transactional @Modifying
    @Query(value = "UPDATE product_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void invalidate(String id);

    boolean existsByLink(String link);

    GoodsReceiptReport findByLink(String link);
    
    // archived
    List<GoodsReceiptReport> findAllByIsArchivedAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isArchived, String start, String end);
    List<GoodsReceiptReport> findAllByIsArchivedAndTimestampLessThanEqualOrderByTimestampDesc(boolean isArchived, String end);
    List<GoodsReceiptReport> findAllByIsArchivedAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isArchived, String start);

    // all and valid
    List<GoodsReceiptReport> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String end);
    List<GoodsReceiptReport> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String start);
    List<GoodsReceiptReport> findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String start, String end);
    List<GoodsReceiptReport> findAllByIsValidAndLinkIsLikeAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String link, String end);
    List<GoodsReceiptReport> findAllByIsValidAndLinkIsLikeAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String link, String start);
    List<GoodsReceiptReport> findAllByIsValidAndLinkIsLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String link, String start, String end);
    List<GoodsReceiptReport> findAllByIsValidAndLinkIsNotLikeAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String s, String end);
    List<GoodsReceiptReport> findAllByIsValidAndLinkIsNotLikeAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String s, String start);
    List<GoodsReceiptReport> findAllByIsValidAndLinkIsNotLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String s, String start, String end);
    List<GoodsReceiptReport> findAllByIsValidAndIdContainsAndLinkIsLikeAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String search, String search1, String end);
    List<GoodsReceiptReport> findAllByIsValidAndIdContainsAndLinkIsLikeAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String search, String search1, String start);
    List<GoodsReceiptReport> findAllByIsValidAndIdContainsAndLinkIsLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String search, String search1, String start, String end);
    List<GoodsReceiptReport> findAllByIsValidAndIdContainsAndLinkIsNotLikeAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String search, String s, String end);
    List<GoodsReceiptReport> findAllByIsValidAndIdContainsAndLinkIsNotLikeAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String search, String s, String start);
    List<GoodsReceiptReport> findAllByIsValidAndIdContainsAndLinkIsNotLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String search, String s, String start, String end);
    List<GoodsReceiptReport> findAllByIdContainsAndIsArchivedAndTimestampLessThanEqualOrderByTimestampDesc(String id, boolean isArchived, String timestamp);
    List<GoodsReceiptReport> findAllByIdContainsAndIsArchivedAndTimestampGreaterThanEqualOrderByTimestampDesc(String id, boolean isArchived, String timestamp);
    List<GoodsReceiptReport> findAllByIdContainsAndIsArchivedAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(String id, boolean isArchived, String timestamp, String timestamp2);
}
