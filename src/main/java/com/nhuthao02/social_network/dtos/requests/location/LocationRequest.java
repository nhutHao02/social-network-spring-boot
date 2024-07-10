package com.nhuthao02.social_network.dtos.requests.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE)
public class LocationRequest {
    String city;
    String district;
    String ward;
    String description;
}
