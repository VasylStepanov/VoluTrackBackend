package com.application.registration.module;

import com.application.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "confirmation_email", schema = "user_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationEmail {

    @Id
    UUID id;
    
    @Column
    boolean confirmed;

    @Column(nullable = false, length = 36)
    private String token;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "fk_user_id")
    User user;
}
