package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.requests.tweet.PostTweetRequest;
import com.nhuthao02.social_network.dtos.requests.tweet.UpdateTweetRequest;
import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.TweetMapper;
import com.nhuthao02.social_network.repositories.TweetRepository;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.ITweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TweetService implements ITweetService {
    @Autowired
    TweetRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetMapper tweetMapper;

    @Override
    public boolean addTweet(PostTweetRequest request) {
        Tweet tweet = tweetMapper.requestToTweet(request);
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        tweet.setUser(user);
        repository.save(tweet);
        return true;
    }

    @Override
    public boolean updateTweet(UpdateTweetRequest request) {
        Tweet tweet = repository.findById(request.getId()).orElseThrow(() -> new AppException(ErrorCode.TWEET_NOT_FOUND));

        tweetMapper.updateTweet(tweet, request);
        repository.save(tweet);
        return true;
    }

    @Override
    public boolean deleteTweet(String id) {
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<TweetResponse> getTweets(Integer page, Integer limit) {
        Page<Tweet> tweets = repository.findAll(PageRequest.of(page, limit));
        return tweetMapper.listToTweets(tweets.getContent());
    }


}
