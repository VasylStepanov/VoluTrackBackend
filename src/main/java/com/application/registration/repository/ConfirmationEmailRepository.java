package com.application.registration.repository;

import com.application.registration.model.ConfirmationEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface ConfirmationEmailRepository extends JpaRepository<ConfirmationEmail, UUID> {

    Optional<ConfirmationEmail> findByToken(String token);

    @Modifying
    @Transactional
    @Query("UPDATE ConfirmationEmail c SET c.confirmed = ?2 WHERE c.token = ?1")
    void updateConfirmationToken(String token, boolean confirmed);

    @Modifying
    @Query("DELETE FROM ConfirmationEmail c WHERE c.token = ?1")
    void deleteByToken(String token);
}
