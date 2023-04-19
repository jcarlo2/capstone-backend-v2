package com.capstone.backend.repository;

import com.capstone.backend.entity.MerchandiseExpiration;
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
    void updateToInactiveExpiration(String id, String timestamp, String reportId);

    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET is_active = 3 WHERE id = ?1 AND timestamp = ?2 AND report_id = ?3",nativeQuery = true)
    void updateToDisposeExpiration(String id, String timestamp, String reportId);

    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET is_active = 0 WHERE id = ?1 AND timestamp = ?2 AND quantity = ?3",nativeQuery = true)
    void doDisposeProduct(String id, String timestamp, Integer quantity);


    @Transactional @Modifying
    @Query(value = "UPDATE product_expiration SET quantity = quantity + ?2 WHERE id = ?1 AND is_active = 1 AND timestamp = ?3",nativeQuery = true)
    void updateExpirationMerchandiseQuantity(String id, Integer quantity, String timestamp);

    List<MerchandiseExpiration> findAllByIdAndIsActiveOrderByTimestamp(String id, String isActive);
    MerchandiseExpiration findAllByIdAndIsActiveAndQuantityAndTimestampOrderByTimestamp(String id, String isActive, Integer quantiy, String timestamp);

    MerchandiseExpiration findByIdAndTimestamp(String id, String timestamp);
}
