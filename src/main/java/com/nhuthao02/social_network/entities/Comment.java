package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "comments")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends BaseEntity {
    String description;
    String urlImage;
    String uuidCommenter;
    // relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_tweet_id")
    Tweet tweet;
}
