package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity(name = "tweets")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Tweet extends BaseEntity {
    String content;
    String urlImage;
    String urlVideo;

    //relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_user_id")
    User user;

    @OneToMany(mappedBy = "tweet")
    List<SaveTweet> saveTweets;

    @OneToMany(mappedBy = "tweet")
    List<RepostTweet> repostTweets;

    @OneToMany(mappedBy = "tweet")
    List<LoveTweet> loveTweets;

    @OneToMany(mappedBy = "tweet")
    List<Comment> comments;


}
