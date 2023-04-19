package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.MerchandiseCategory;
import com.example.capstonebackendv2.helper.enums.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MerchandiseCategoryRepository extends CrudRepository<MerchandiseCategory, String> {
    boolean existsByIdAndCategory(String id, Category category);
    List<MerchandiseCategory> findAllByIdOrderByCategory(String id);

    @Transactional @Modifying
    @Query(value = "DELETE FROM product_category WHERE id = ?1",nativeQuery = true)
    void deleteAllCategoryById(String id);
}
