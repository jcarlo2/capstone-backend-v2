package com.capstone.backend.repository;

import com.capstone.backend.entity.InventoryLossReport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryLossReportRepository extends CrudRepository<InventoryLossReport,String> {
    InventoryLossReport findByLink(String link);
    boolean existsByLink(String link);

    @Transactional
    @Modifying
    @Query(value = "UPDATE null_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void invalidate(String id);

    @Transactional @Modifying
    @Query(value = "UPDATE null_report SET is_archived = 1 WHERE id = ?1",nativeQuery = true)
    void archive(String id);

    // all, archived and valid
    List<InventoryLossReport> findAllByIsArchivedAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isArchived, String start, String end);
    List<InventoryLossReport> findAllByIsArchivedAndTimestampLessThanEqualOrderByTimestampDesc(boolean isArchived, String end);
    List<InventoryLossReport> findAllByIsArchivedAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isArchived, String start);

    // all and valid
    List<InventoryLossReport> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String end);
    List<InventoryLossReport> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String start);
    List<InventoryLossReport> findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String start, String end);
    List<InventoryLossReport> findAllByIsValidAndLinkIsLikeAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String link, String end);
    List<InventoryLossReport> findAllByIsValidAndLinkIsLikeAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String link, String start);
    List<InventoryLossReport> findAllByIsValidAndLinkIsLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String link, String start, String end);
    List<InventoryLossReport> findAllByIsValidAndLinkIsNotLikeAndTimestampLessThanEqualAndReasonIsNotLikeOrderByTimestampDesc(boolean isValid, String link, String end, String reason);
    List<InventoryLossReport> findAllByIsValidAndLinkIsNotLikeAndTimestampGreaterThanEqualAndReasonIsNotLikeOrderByTimestampDesc(boolean isValid, String link, String start, String reason);
    List<InventoryLossReport> findAllByIsValidAndLinkIsNotLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualAndReasonIsNotLikeOrderByTimestampDesc(boolean isValid, String link, String start, String end, String reason);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndLinkIsLikeAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String search, String search1, String end);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndLinkIsLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String search, String search1, String start, String end);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndLinkIsLikeAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String search, String search1, String start);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndLinkIsNotLikeAndTimestampLessThanEqualAndReasonIsNotLikeOrderByTimestampDesc(boolean isValid, String search, String s, String end, String reason);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndLinkIsNotLikeAndTimestampGreaterThanEqualAndReasonIsNotLikeOrderByTimestampDesc(boolean isValid, String search, String s, String start, String reason);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndLinkIsNotLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualAndReasonIsNotLikeOrderByTimestampDesc(boolean isValid, String search, String s, String start, String end, String reason);
    List<InventoryLossReport> findAllByIsValidAndReasonIsLikeAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String disposed, String end);
    List<InventoryLossReport> findAllByIsValidAndReasonIsLikeAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String disposed, String start);
    List<InventoryLossReport> findAllByIsValidAndReasonIsLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String disposed, String start, String end);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndReasonIsLikeAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String search, String disposed, String end);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndReasonIsLikeAndTimestampGreaterThanEqualOrderByTimestampDesc(boolean isValid, String search, String disposed, String start);
    List<InventoryLossReport> findAllByIsValidAndIdContainsAndReasonIsLikeAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(boolean isValid, String search, String disposed, String start, String end);
    List<InventoryLossReport> findAllByIdContainsAndIsArchivedAndTimestampLessThanEqualOrderByTimestampDesc(String search, boolean s, String end);
    List<InventoryLossReport> findAllByIdContainsAndIsArchivedAndTimestampGreaterThanEqualOrderByTimestampDesc(String search, boolean s, String start);
    List<InventoryLossReport> findAllByIdContainsAndIsArchivedAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(String search, boolean s, String start, String end);
}
