package com.nhuthao02.social_network.dtos.requests.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "User login request")
public class UserLoginRequest {
    @Schema(description = "Username of account", example = "userName")
    String userName;
    @Schema(description = "Password of account", example = "password@123")
    String password;
}
