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

@Schema(description = "User creation request")
public class UserCreationRequest {
    @Schema(description = "Fullname of user", example = "Nguyen Van A")
    String fullName;
    @Schema(description = "Username of account", example = "user1")
    String userName;
    @Schema(description = "Password of account", example = "Password123@")
    String password;
    @Schema(description = "Sex of user: true for male, false for female", example = "true")
    Boolean sex;
}
