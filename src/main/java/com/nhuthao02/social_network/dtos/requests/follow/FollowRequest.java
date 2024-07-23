package com.nhuthao02.social_network.dtos.requests.follow;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowRequest {
    String uuidUserFollower;
    String uuidUserFollowing;
}
