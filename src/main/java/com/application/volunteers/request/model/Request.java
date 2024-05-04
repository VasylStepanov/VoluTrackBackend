package com.application.volunteers.request.model;

import com.application.config.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity(name = "request")
@Table(name = "requests", schema = "volunteer_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request extends BaseEntity {

    @OneToMany(mappedBy = "request")
    List<RequestItem> requestItems;
}
