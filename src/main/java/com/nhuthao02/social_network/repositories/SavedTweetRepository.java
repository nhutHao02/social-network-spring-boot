package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.SaveTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavedTweetRepository extends JpaRepository<SaveTweet, String> {
    Optional<SaveTweet> findSaveTweetByUserAndTweet(User user, Tweet tweet);

    Long countByTweet(Tweet tweet);

    Page<SaveTweet> findAllByUser(User user, Pageable pageable);
}
