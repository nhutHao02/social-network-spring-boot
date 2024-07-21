package com.nhuthao02.social_network.dtos.requests.comment;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddCommentRequest {
    String description;
    String urlImage;
    String uuidCommenter;
    String tweetId;
}
