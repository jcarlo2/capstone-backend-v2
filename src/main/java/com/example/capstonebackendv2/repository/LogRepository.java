package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.Log;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LogRepository extends CrudRepository<Log,String> {
    List<Log> findAllByOrderByTimestampDesc();
    List<Log> findAllByIsDeletable(Boolean isDeletable);

    @Transactional @Modifying
    void removeAllByIsDeletable(Boolean isDeletable);
}
