package com.application.registration.model;

import com.application.config.BaseEntity;
import com.application.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "confirmation_email", schema = "user_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfirmationEmail extends BaseEntity {

    @Column
    boolean confirmed;

    @Column(nullable = false, length = 36)
    String token;

    @Column(nullable = false)
    LocalDateTime expiresAt;

    @MapsId
    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "fk_user_id")
    User user;
}
