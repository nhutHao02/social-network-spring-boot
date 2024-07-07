package com.nhuthao02.social_network.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE)
public class UserLoginRequest {
    String userName;
    String password;
}
