package com.nhuthao02.social_network.dtos.requests.user;

import com.nhuthao02.social_network.dtos.requests.location.LocationRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "User update request")
public class UserUpdateRequest {
    @Schema(description = "Bio of user", example = "Hello world")
    String bio;
    @Schema(description = "Full name", example = "Tran Van A")
    String fullName;
    @Schema(description = "Email", example = "example@gmail.com")
    String email;
    @Schema(description = "Sex of user", example = "true: Male, false: Female")
    Boolean sex;
    @Schema(description = "Url Avt of user", example = "https://example.com")
    String urlAvt;
    @Schema(description = "Url background of user", example = "https://example.com")
    String urlBackground;
    @Schema(description = "Location")
    LocationRequest locationRequest;
}
