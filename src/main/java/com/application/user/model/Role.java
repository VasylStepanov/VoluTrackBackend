package com.application.user.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "role")
@Table(name = "roles", schema = "user_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @GeneratedValue
    Integer id;

    @Column(name = "name", length = 32, nullable = false)
    String name;
}
