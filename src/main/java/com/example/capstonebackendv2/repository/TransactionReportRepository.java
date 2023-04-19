package com.capstone.backend.repository;

import com.capstone.backend.entity.TransactionReport;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionReportRepository extends CrudRepository<TransactionReport, String> {
    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_valid = 0 WHERE id = ?1",nativeQuery = true)
    void invalidate(String id);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_archived = 1 WHERE id = ?1",nativeQuery = true)
    void archive(String id);

    @Modifying @Transactional
    @Query(value = "UPDATE transaction_report SET is_valid = 1 WHERE id = ?1",nativeQuery = true)
    void validate(String id);

    // all
    List<TransactionReport> findAllByOrderByTimestampDesc();
    List<TransactionReport> findAllByTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(String start, String end);
    List<TransactionReport> findAllByTimestampGreaterThanEqualOrderByTimestampDesc(String start);
    List<TransactionReport> findAllByTimestampLessThanEqualOrderByTimestampDesc(String end);
    List<TransactionReport> findAllByIsValidAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(String isValid, String start, String end);

    // valid
    List<TransactionReport> findAllByIsValidOrderByTimestampDesc(String isValid);
    List<TransactionReport> findAllByIsValidAndTimestampGreaterThanEqualOrderByTimestampDesc(String isValid, String start);
    List<TransactionReport> findAllByIsValidAndTimestampLessThanEqualOrderByTimestampDesc(String isValid, String end);
    List<TransactionReport> findAllByIdContainsAndIsValidOrderByTimestampDesc(String search, String isValid);
    List<TransactionReport> findAllByIdContainsAndIsArchivedOrderByTimestampDesc(String search, String isArchived);
    List<TransactionReport> findAllByIdContainsOrderByTimestampDesc(String search);
    List<TransactionReport> findAllByIdContainsOrBirLikeOrderByTimestampDesc(String search, int bir);
    List<TransactionReport> findAllByIdContainsOrBirLikeAndIsValidOrderByTimestampDesc(String search, int bir, String isValid);

    // archived
    List<TransactionReport> findAllByIsArchivedOrderByTimestampDesc(String isArchived);
    List<TransactionReport> findAllByIsArchivedAndTimestampGreaterThanEqualAndTimestampLessThanEqualOrderByTimestampDesc(String isArchived, String start, String end);
    List<TransactionReport> findAllByIsArchivedAndTimestampLessThanEqualOrderByTimestampDesc(String isArchived, String end);
    List<TransactionReport> findAllByIsArchivedAndTimestampGreaterThanEqualOrderByTimestampDesc(String isArchived, String start);
    List<TransactionReport> findAllByIdContainsOrBirLikeAndIsArchivedOrderByTimestampDesc(String search, int bir, String isArchived);
}
