package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Entity(name = "users")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity {
    @NotNull
    @Column(nullable = false)
    String userName;
    String bio;

    @NotNull
    @Column(nullable = false)
    String password;
    String fullName;
    String email;

    @NotNull
    @Column(nullable = false)
    Boolean sex;

    String urlAvt;
    String urlBackground;

    // relationship
    @OneToOne
    @JoinColumn(name = "user_location_id")
    Location location;

    @OneToMany(mappedBy = "user")
    List<Notification> notifications;

    @OneToMany(mappedBy = "user")
    List<Tweet> tweets;

    @OneToMany(mappedBy = "user")
    List<SaveTweet> saveTweets;

    @OneToMany(mappedBy = "user")
    List<RepostTweet> repostTweets;

    @OneToMany(mappedBy = "user")
    List<LoveTweet> loveTweets;

    @OneToMany(mappedBy = "user")
    List<RecentMessage> recentMessages;
}
