package com.nhuthao02.social_network.dtos.responses;

import com.nhuthao02.social_network.dtos.responses.location.LocationResponse;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE)
public class UserInfoResponse {
    String userName;
    String bio;
    String fullName;
    String email;
    Boolean sex;
    String urlAvt;
    String urlBackground;
    LocationResponse location;
}
