package com.application.authentication.token;

import com.application.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "token")
@Table(name = "tokens", schema = "user_data", uniqueConstraints = @UniqueConstraint(name = "uk_ip_address", columnNames = {"ip_address"}))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "refresh_token", nullable = false, columnDefinition = "VARCHAR")
    String refreshToken;

    @Column(name = "ip_address", length = 15, nullable = false)
    String ipAddress;

    @Column(name = "expires_at", nullable = false)
    Date expiresAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}