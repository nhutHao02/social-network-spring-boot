package com.nhuthao02.social_network.dtos.responses.notif;

import com.nhuthao02.social_network.dtos.responses.user.UserTweetResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {
    String content;
    UserTweetResponse user;
}
