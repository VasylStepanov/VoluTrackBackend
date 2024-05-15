package com.application.content.volunteers.car.model;

import com.application.config.BaseEntity;
import com.application.content.volunteers.volunteer.model.Volunteer;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "car")
@Table(name = "cars", schema = "volunteer_data", uniqueConstraints = @UniqueConstraint(name = "uk_car_number", columnNames = {"number"}))
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car extends BaseEntity {

    @Column(name = "number", length = 16, nullable = false, unique = true)
    String number;

    @Column(name = "description", length = 64)
    String description;

    @Column(name = "carrying_kg", nullable = false)
    Integer carryingKg;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    CarType carType;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    Volunteer volunteer;
}
