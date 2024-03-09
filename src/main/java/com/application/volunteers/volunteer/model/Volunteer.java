package com.application.volunteers.volunteer.model;

import com.application.config.BaseEntity;
import com.application.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "volunteer", schema = "volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Volunteer extends BaseEntity {

    @Builder.Default
    @Column(name = "help_counter")
    int helpCounter = 0;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;
}
