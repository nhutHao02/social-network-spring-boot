package com.nhuthao02.social_network.dtos.responses.message;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageResponse {
    String id;
    String uuidSender;
    String content;
    String urlImage;
    String urlVideo;
}
