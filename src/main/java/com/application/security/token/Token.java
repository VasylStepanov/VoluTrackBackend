package com.application.security.token;

import com.application.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "token")
@Table(name = "tokens", schema = "user_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "refresh_token", nullable = false, columnDefinition = "VARCHAR")
    String refreshToken;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;
}