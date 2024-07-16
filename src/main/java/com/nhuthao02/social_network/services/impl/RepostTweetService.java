package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.entities.LoveTweet;
import com.nhuthao02.social_network.entities.RepostTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.repositories.RepostTweetRepository;
import com.nhuthao02.social_network.repositories.TweetRepository;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.IRepostTweetService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepostTweetService implements IRepostTweetService {
    @Autowired
    RepostTweetRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    @Override
    public boolean repost(String userName, String tweetId) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new AppException(ErrorCode.TWEET_NOT_FOUND));
        repository.save(RepostTweet.builder().user(user).tweet(tweet).build());
        return true;
    }

    @Override
    public boolean deleteTweetReposted(String userName, String tweetId) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new AppException(ErrorCode.TWEET_NOT_FOUND));
        Optional<RepostTweet> repostTweet = repository.findRepostTweetByUserAndTweet(user, tweet);
        if (repostTweet.isEmpty()) {
            throw new AppException(ErrorCode.TWEET_REPOSTED_NOT_FOUND);
        }
        repository.delete(repostTweet.get());
        return true;
    }
}
