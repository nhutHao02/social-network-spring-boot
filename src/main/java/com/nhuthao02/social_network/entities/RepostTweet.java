package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "repostTweets")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepostTweet extends BaseEntity {
    // relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repostTweet_user_id")
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repostTweet_tweet_id")
    Tweet tweet;
}
