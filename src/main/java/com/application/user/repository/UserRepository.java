package com.application.user.repository;

import com.application.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM user u WHERE u.email = ?1 OR u.phoneNumber = ?2")
    Optional<User> findByData(String email, String phoneNumber);
}
