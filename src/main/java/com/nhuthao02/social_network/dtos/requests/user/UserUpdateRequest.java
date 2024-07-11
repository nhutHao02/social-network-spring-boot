package com.nhuthao02.social_network.dtos.requests.user;

import com.nhuthao02.social_network.dtos.requests.location.LocationRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE)
public class UserUpdateRequest {
    String bio;
    String fullName;
    String email;
    Boolean sex;
    String urlAvt;
    String urlBackground;
    LocationRequest locationRequest;
}
