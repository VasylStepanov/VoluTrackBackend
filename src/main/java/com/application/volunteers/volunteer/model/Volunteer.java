package com.application.volunteers.volunteer.model;

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
@Table(name = "volunteer", schema = "volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Volunteer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @Builder.Default
    @Column(name = "help_counter")
    int helpCounter = 0;

    @Builder.Default
    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
