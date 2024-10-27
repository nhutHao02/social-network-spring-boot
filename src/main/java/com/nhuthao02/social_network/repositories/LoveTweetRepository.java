package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.LoveTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoveTweetRepository extends JpaRepository<LoveTweet, String> {
    Optional<LoveTweet> findLoveTweetByUserAndTweet(User user, Tweet tweet);

    Page<LoveTweet> findAllByUser(User user, Pageable pageable);

    Long countByTweet(Tweet tweet);

    boolean existsLoveTweetByUserAndTweet(User user, Tweet tweet);
}
