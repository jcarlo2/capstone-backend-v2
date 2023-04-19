package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.MerchandiseExpiration;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MerchandiseExpirationRepository extends CrudRepository<MerchandiseExpiration, String> {
    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET is_active = 0 WHERE id = ?1 AND timestamp = ?2 AND report_id = ?3",nativeQuery = true)
    void toInactiveWithTimestamp(String id, String timestamp, String reportId);

    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET is_active = 3 WHERE id = ?1 AND timestamp = ?2 AND report_id = ?3",nativeQuery = true)
    void updateToDisposeExpiration(String id, String timestamp, String reportId);

    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET is_active = 0 WHERE id = ?1 AND timestamp = ?2 AND quantity = ?3",nativeQuery = true)
    void doDisposeProduct(String id, String timestamp, Integer quantity);


    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET quantity = quantity + ?2 WHERE id = ?1 AND is_active = 1 AND timestamp = ?3",nativeQuery = true)
    void updateQuantity(String id, Integer quantity, String timestamp);

    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET is_active = false WHERE id = ?1 AND is_active = 1 AND report_id = ?2 ",nativeQuery = true)
    void setInactive(String id, String reportId);

    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET is_active = true, quantity = ?2 WHERE id = ?1",nativeQuery = true)
    void updateQuantityAndSetToActive(String id, Integer quantity);

    List<MerchandiseExpiration> findAllByIdAndIsActiveOrderByTimestampDesc(String id, Boolean isActive);
    List<MerchandiseExpiration> findAllByIdAndIsActiveOrderByTimestamp(String id, Boolean isActive);
    MerchandiseExpiration findByIdAndReportIdAndIsActiveOrderByTimestampDesc(String id, String reportId, Boolean isActive);
    boolean existsByIdAndReportIdAndIsActiveAndQuantityGreaterThan(String id, String reportId, Boolean isActive, Integer quantity);
}
