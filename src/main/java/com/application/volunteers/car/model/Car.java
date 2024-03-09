package com.application.volunteers.car.model;

import com.application.config.BaseEntity;
import com.application.volunteers.volunteer.model.Volunteer;
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
@Table(name = "car", schema = "volunteer_data", uniqueConstraints = {
        @UniqueConstraint(name = "uk_car_number", columnNames = {"car_number"}),
        @UniqueConstraint(name = "uk_car_volunteer_id", columnNames = {"volunteer_id"})
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car extends BaseEntity {

    @Column(name = "car_number", length = 16, nullable = false, unique = true)
    String carNumber;

    @Column(name = "carrying_kg", nullable = false)
    Integer carryingKg;

    @Column(name = "car_type", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    CarType carType;

    @Setter(AccessLevel.NONE)
    @OneToOne
    @JoinColumn(name = "volunteer_id", nullable = false, unique = true)
    Volunteer volunteer;
}
