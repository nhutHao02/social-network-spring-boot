package com.nhuthao02.social_network.dtos.requests.message;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetMessageRequest {
    String uuidSender;
    String uuidReceiver;
}
