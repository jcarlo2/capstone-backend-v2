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
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    User findByUsernameAndPasswordAndIsActive(String username, String password,boolean isActive);
    List<User> findAllByUsernameNotContainsAndFirstNameNotContainingAndLastNameNotContainingAndIsActive(String username, String firstName, String lastName,boolean isActive);

    @Transactional @Modifying
    @Query(value = "UPDATE user SET is_active = 0 WHERE id = ?1",nativeQuery = true)
    void archiveUserAccount(String id);

    @Transactional @Modifying
    void removeByUsername(String username);

    @Transactional @Modifying
    @Query(value = "UPDATE user SET password = ?3 WHERE id = ?1 AND password = ?2",nativeQuery = true)
    void changePassword(String username, String oldPassword, String newPassword);
}
