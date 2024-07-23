package com.nhuthao02.social_network.dtos.responses.comment;

import com.nhuthao02.social_network.dtos.responses.user.UserTweetResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse {
    String description;
    String urlImage;
    String uuidCommenter;
    UserTweetResponse user;
}
