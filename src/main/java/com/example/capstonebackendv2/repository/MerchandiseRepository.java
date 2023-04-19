package com.capstone.backend.repository;

import com.capstone.backend.entity.Merchandise;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MerchandiseRepository extends CrudRepository<Merchandise, String> {
    List<Merchandise> findAllByIsActiveOrderById(String isActive);
    List<Merchandise> findAllByIsActiveOrderByName(String isActive);
    List<Merchandise> findAllByIsActiveOrderByQuantityPerPiecesDesc(String isActive);
    List<Merchandise> findAllByIsActiveOrderByQuantityPerPieces(String isActive);
    List<Merchandise> findAllByIsActiveOrderByPrice(String isActive);
    List<Merchandise> findAllByIsActiveOrderByPriceDesc(String isActive);
    List<Merchandise> findAllByPriceLessThanEqualAndIsActiveOrderByPriceDesc(BigDecimal search,String isActive);

    @Modifying @Transactional
    @Query(value = "UPDATE product SET quantity_per_pieces = product.quantity_per_pieces + ?1 WHERE product.id = ?2",nativeQuery = true)
    void updateProductQuantity(Integer quantity, String id);

    @Modifying @Transactional
    @Query(value = "UPDATE product SET name = ?2, price = ?3, pieces_per_box = ?4 WHERE product.id = ?1",nativeQuery = true)
    void updateProduct(String id, String name, BigDecimal price, Integer box);

    @Transactional @Modifying
    @Query(value = "UPDATE product SET is_active = ?2 WHERE id = ?1",nativeQuery = true)
    void setProductIsActive(String id, String isActive);

    @Transactional @Modifying
    @Query(value = "UPDATE product SET is_active = 1, quantity_per_pieces = 0 WHERE id = ?1",nativeQuery = true)
    void setProductActiveWithZeroQuantity(String id);

    @Transactional @Modifying
    @Query(value = "UPDATE product SET price = ?2 WHERE id = ?1",nativeQuery = true)
    void updateProductPrice(String id, BigDecimal price);
}
