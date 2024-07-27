package com.nhuthao02.social_network.dtos.requests.message;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageRequest {
    String uuidSender;
    String uuidReceiver;
    String content;
    String urlImage;
    String urlVideo;
}
