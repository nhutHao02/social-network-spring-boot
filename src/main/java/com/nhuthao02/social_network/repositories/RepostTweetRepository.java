package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.RepostTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RepostTweetRepository extends JpaRepository<RepostTweet, String> {
    Optional<RepostTweet> findRepostTweetByUserAndTweet(User user, Tweet tweet);
}
