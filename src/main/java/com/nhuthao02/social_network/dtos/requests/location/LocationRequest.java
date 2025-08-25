package com.nhuthao02.social_network.dtos.requests.location;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Location request")
public class LocationRequest {
    @Schema(description = "City of location", example = "Ho Chi Minh")
    String city;
    @Schema(description = "District of location", example = "Thu Duc")
    String district;
    @Schema(description = "Ward of location", example = "Binh Trung")
    String ward;
    @Schema(description = "Description of location", example = "22/2, 38 street")
    String description;
}
