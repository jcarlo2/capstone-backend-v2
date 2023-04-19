package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.InventoryLossReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface InventoryLossReportRepository extends CrudRepository<InventoryLossReport,String> {

    List<InventoryLossReport> findAllByIsValidAndIsArchivedAndIdContainingIgnoreCaseAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc
            (Boolean valid, Boolean archived, String search, String start, String end, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE null_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void invalidate(String id);

    @Transactional @Modifying
    @Query(value = "UPDATE null_report SET is_archived = 1 WHERE id = ?1",nativeQuery = true)
    void archive(String id);
}
