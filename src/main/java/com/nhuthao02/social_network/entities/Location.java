package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "locations")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location extends BaseEntity {
    String city;
    String district;
    String ward;
    String description;

    // relationship
    @OneToOne(mappedBy = "location")
    User user;
}
