package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.entities.LoveTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.repositories.LoveTweetRepository;
import com.nhuthao02.social_network.repositories.TweetRepository;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.ILoveTweetService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoveTweetService implements ILoveTweetService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    LoveTweetRepository repository;

    @Override
    public boolean loveTweet(String userName, String tweetId) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new AppException(ErrorCode.TWEET_NOT_FOUND));
        repository.save(LoveTweet.builder().user(user).tweet(tweet).build());
        return true;
    }

    @Override
    public boolean deleteTweetLoved(String userName, String tweetId) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new AppException(ErrorCode.TWEET_NOT_FOUND));
        Optional<LoveTweet> loveTweet = repository.findLoveTweetByUserAndTweet(user, tweet);
        if (loveTweet.isEmpty()) {
            throw new AppException(ErrorCode.TWEET_LOVED_NOT_FOUND);
        }
        repository.delete(loveTweet.get());
        return true;
    }

    @Override
    public List<TweetResponse> getTweetLoved(String userName, Integer page, Integer limit) {
//        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
//        Page<LoveTweet> loveTweets = repository.findAll(PageRequest.of(page, limit));
//        return tweetMapper.listToTweets(tweets.getContent());
        return null;
    }

}
