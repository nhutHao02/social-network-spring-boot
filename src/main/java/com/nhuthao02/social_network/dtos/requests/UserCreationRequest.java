package com.nhuthao02.social_network.dtos.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PACKAGE)
public class UserCreationRequest {
    String userName;
    String password;
    Boolean sex;
}
