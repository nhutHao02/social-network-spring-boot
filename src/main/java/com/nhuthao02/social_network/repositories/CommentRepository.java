package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.Comment;
import com.nhuthao02.social_network.entities.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
    Page<Comment> findAllByTweet_Id(String tweetId, Pageable pageable);

    Long countByTweet(Tweet tweet);
}
