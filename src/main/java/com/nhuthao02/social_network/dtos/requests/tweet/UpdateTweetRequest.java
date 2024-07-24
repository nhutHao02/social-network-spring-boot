package com.nhuthao02.social_network.dtos.requests.tweet;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateTweetRequest {
    String id;
    String content;
    String urlImage;
    String urlVideo;
    String userId;
}
