package com.application.authentication.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    Optional<Token> findByIpAddress(String ipAddress);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM user_data.tokens WHERE refresh_token = :refresh_token")
    void removeByRefreshToken(@Param("refresh_token") String refreshToken);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM user_data.tokens WHERE id = :id")
    void removeById(@Param("id") UUID id);
}
