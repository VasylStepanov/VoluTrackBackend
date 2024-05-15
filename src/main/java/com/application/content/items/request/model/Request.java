package com.application.content.items.request.model;

import com.application.config.BaseEntity;
import jakarta.persistence.Entity;
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
@Table(name = "requests", schema = "item_data")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Request extends BaseEntity {

    @OneToMany(mappedBy = "request")
    List<RequestItem> requestItems;
}
