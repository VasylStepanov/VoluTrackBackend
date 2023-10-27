package com.application.user.model;

import com.application.registration.module.ConfirmationEmail;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"confirmationEmail"})
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "user")
@Table(schema = "user_data", name = "users", uniqueConstraints = @UniqueConstraint(name = "uk_email", columnNames = { "email" }))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Column(name = "full_name", length = 64, nullable = false)
    String fullName;

    @Column(length = 80, nullable = false)
    String email;

    @Column(length = 128, nullable = false)
    String password;

    @Enumerated(EnumType.STRING)
    @Column(length = 32, nullable = false)
    UserRole role;

    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    ConfirmationEmail confirmationEmail;
}