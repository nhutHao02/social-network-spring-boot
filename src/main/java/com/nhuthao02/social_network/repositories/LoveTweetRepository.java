package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.entities.LoveTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoveTweetRepository extends JpaRepository<LoveTweet, String> {
    Optional<LoveTweet> findLoveTweetByUserAndTweet(User user, Tweet tweet);
}
