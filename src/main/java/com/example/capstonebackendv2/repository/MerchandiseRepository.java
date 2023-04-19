package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.Merchandise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface MerchandiseRepository extends CrudRepository<Merchandise, String> {

    @Modifying @Transactional
    @Query(value = "UPDATE product SET quantity_per_pieces = product.quantity_per_pieces + ?2 WHERE product.id = ?1",nativeQuery = true)
    void updateQuantity(String id,Integer quantity);

    @Transactional @Modifying
    @Query(value = "UPDATE product SET price = ?2 WHERE id = ?1",nativeQuery = true)
    void updatePrice(String id, BigDecimal price);

    Page<Merchandise> findAllByIdContainsIgnoreCaseOrDescriptionContainsIgnoreCase(String id, String description, Pageable pageable);
    Page<Merchandise> findAllByPriceLessThanEqualAndIsActive(BigDecimal search, boolean isActive, Pageable pageable);
    Merchandise findByIdAndIsActive(String id, Boolean isActive);
    boolean existsByIdIgnoreCase(String id);
}
