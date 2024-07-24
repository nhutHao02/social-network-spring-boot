package com.nhuthao02.social_network.dtos.responses.tweet;

import com.nhuthao02.social_network.dtos.responses.user.UserTweetResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TweetResponse {
    String id;
    String content;
    String urlImage;
    String urlVideo;
    UserTweetResponse user;
    Long totalSaved;
    Long totalRepost;
    Long totalLove;
    Long totalComment;
    Date createAt;
}
