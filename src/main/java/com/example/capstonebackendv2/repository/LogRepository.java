package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log,String> {
    List<Log> findAllByIsDeletableOrderByTimestampDesc(Boolean isDeletable);

    @Transactional @Modifying
    @Query(value = "UPDATE log SET is_archived = 1 WHERE is_deletable = 0",nativeQuery = true)
    void adminArchive();
}
