package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.TransactionReport;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionReportRepository extends CrudRepository<TransactionReport, String> {
    List<TransactionReport> findAllByIsValidAndIsArchivedAndIdContainingIgnoreCaseAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc
            (boolean valid, boolean archived, String search, String start, String end, Pageable pageable);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void invalidate(String id);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_archived = 1 WHERE id = ?1",nativeQuery = true)
    void archive(String id);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_valid = 1 WHERE id = ?1",nativeQuery = true)
    void validate(String id);

    List<TransactionReport> findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(Boolean isValid, String start, String end);

}
