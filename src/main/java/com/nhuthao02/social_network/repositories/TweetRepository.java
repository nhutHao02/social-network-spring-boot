package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, String> {
    Page<Tweet> findAllByUser(User user, Pageable pageable);

}
