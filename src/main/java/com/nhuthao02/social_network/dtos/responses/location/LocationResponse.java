package com.nhuthao02.social_network.dtos.responses.location;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationResponse {
    String city;
    String district;
    String ward;
    String description;
}
