package com.example.capstonebackendv2.repository;

import com.example.capstonebackendv2.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,String> {
    boolean existsUserByUsernameAndPasswordAndIsActive(String username, String password, boolean isActive);
    boolean existsByUsername(String id);
    User findByUsernameAndPasswordAndIsActive(String username, String password,boolean isActive);
    List<User> findAllByUsernameNotContainsAndFirstNameNotContainingAndLastNameNotContainingAndIsActive
            (String id, String firstName, String lastName,Boolean isActive);
    @Transactional @Modifying
    @Query(value = "UPDATE user SET is_active = 0 WHERE id = ?1",nativeQuery = true)
    void archiveUserAccount(String id);

    @Transactional @Modifying
    @Query(value = "UPDATE user SET password = ?3 WHERE id = ?1 AND password = ?2",nativeQuery = true)
    void changePassword(String username, String oldPassword, String newPassword);

    List<User> findAllByIsActiveOrderByUsername(Boolean isActive);
}
