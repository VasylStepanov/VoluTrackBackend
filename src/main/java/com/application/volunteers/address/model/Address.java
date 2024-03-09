package com.application.volunteers.address.model;

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
@Entity(name = "address")
@Table(name = "address", schema = "volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends BaseEntity {

    @Column(name = "region", length = 32, nullable = false)
    String region;

    @Column(name = "settlement", length = 64, nullable = false)
    String settlement;

    @Column(name = "location", length = 128, nullable = false)
    String location;

    @Column(name = "coordinates_latitude")
    Double coordinatesLatitude;

    @Column(name = "coordinates_longitude")
    Double coordinatesLongitude;

    @Setter(AccessLevel.NONE)
    @OneToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    Volunteer volunteer;
}
