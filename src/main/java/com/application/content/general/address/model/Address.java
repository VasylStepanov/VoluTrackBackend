package com.application.content.general.address.model;

import com.application.config.BaseEntity;
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
@Table(name = "addresses", schema = "general_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Address extends BaseEntity {

    @Column(name = "address", length = 256)
    String address;

    @Column(name = "coordinates_longitude", nullable = false)
    Double coordinatesLongitude;

    @Column(name = "coordinates_latitude", nullable = false)
    Double coordinatesLatitude;
}
