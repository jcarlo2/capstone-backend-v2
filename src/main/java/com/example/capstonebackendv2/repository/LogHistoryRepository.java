package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.LogHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogHistoryRepository extends CrudRepository<LogHistory,String> {
}
