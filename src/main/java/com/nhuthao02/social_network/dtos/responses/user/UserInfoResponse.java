package com.nhuthao02.social_network.dtos.responses.user;

import com.nhuthao02.social_network.dtos.responses.location.LocationResponse;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoResponse {
    String id;
    String userName;
    String bio;
    String fullName;
    String email;
    Boolean sex;
    String urlAvt;
    String urlBackground;
    LocationResponse location;
    Date createAt;
}
