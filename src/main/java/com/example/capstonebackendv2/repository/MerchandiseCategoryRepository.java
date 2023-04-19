package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.helper.enums.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MerchandiseCategory extends CrudRepository<com.example.capstonebackendv2.entity.MerchandiseCategory, String> {
    boolean existsByIdAndCategory(String id, Category category);
    List<com.example.capstonebackendv2.entity.MerchandiseCategory> findAllByIdOrderByCategory(String id);
    List<com.example.capstonebackendv2.entity.MerchandiseCategory> findAllById(String id);

    @Transactional @Modifying
    void deleteByIdAndCategory(String id, Category category);
}
