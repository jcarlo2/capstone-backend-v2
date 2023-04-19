package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.MerchandiseHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchandiseHistoryRepository extends CrudRepository<MerchandiseHistory, String> {
    List<MerchandiseHistory> findAllByIdOrderByCreatedAtDesc(String id);
}
