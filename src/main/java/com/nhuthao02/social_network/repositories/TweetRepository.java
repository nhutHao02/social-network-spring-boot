package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, String> {

}
