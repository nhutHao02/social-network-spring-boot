package com.nhuthao02.social_network.dtos.requests.notif;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationRequest {
    String content;
    String uuidReceiver;
    String uuidSender;
}
