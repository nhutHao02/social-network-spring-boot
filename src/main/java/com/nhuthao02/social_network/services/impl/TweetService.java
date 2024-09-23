package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.requests.tweet.PostTweetRequest;
import com.nhuthao02.social_network.dtos.requests.tweet.UpdateTweetRequest;
import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.entities.SaveTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.TweetMapper;
import com.nhuthao02.social_network.mapper.UserMapper;
import com.nhuthao02.social_network.repositories.*;
import com.nhuthao02.social_network.services.ITweetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TweetService implements ITweetService {
    final
    TweetRepository repository;

    final
    SavedTweetRepository savedTweetRepository;

    final
    UserRepository userRepository;

    final
    LoveTweetRepository loveTweetRepository;

    final
    RepostTweetRepository repostTweetRepository;

    final
    CommentRepository commentRepository;

    final
    TweetMapper tweetMapper;

    final
    UserMapper userMapper;

    public TweetService(TweetRepository repository, SavedTweetRepository savedTweetRepository, UserRepository userRepository, LoveTweetRepository loveTweetRepository, RepostTweetRepository repostTweetRepository, CommentRepository commentRepository, TweetMapper tweetMapper, UserMapper userMapper) {
        this.repository = repository;
        this.savedTweetRepository = savedTweetRepository;
        this.userRepository = userRepository;
        this.loveTweetRepository = loveTweetRepository;
        this.repostTweetRepository = repostTweetRepository;
        this.commentRepository = commentRepository;
        this.tweetMapper = tweetMapper;
        this.userMapper = userMapper;
    }

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
        List<TweetResponse> listRs = new ArrayList<>();
        Page<Tweet> tweets = repository.findAll(PageRequest.of(page, limit));
        for (Tweet tweet :
                tweets.getContent()) {
            // map tweet to tweetResponse
            TweetResponse tweetResponse = tweetMapper.tweetToTweetResponse(tweet);
            // map user to userResponse
            tweetResponse.setUser(userMapper.userToUserTweetResponse(tweet.getUser()));
            // count LovedTweet
            tweetResponse.setTotalLove(loveTweetRepository.countByTweet(tweet));
            // count Saved
            tweetResponse.setTotalSaved(savedTweetRepository.countByTweet(tweet));
            // count Repost
            tweetResponse.setTotalRepost(repostTweetRepository.countByTweet(tweet));
            // count Comment
            tweetResponse.setTotalComment(commentRepository.countByTweet(tweet));

            listRs.add(tweetResponse);
        }
        return listRs;
    }

    @Override
    public List<TweetResponse> getTweetsByUserID(String userID, Integer page, Integer limit) {
        List<TweetResponse> listRs = new ArrayList<>();
        User user = userRepository.findById(userID).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Page<Tweet> tweets = repository.findAllByUser(user, PageRequest.of(page, limit));
        for (Tweet tweet :
                tweets.getContent()) {
            // map tweet to tweetResponse
            TweetResponse tweetResponse = tweetMapper.tweetToTweetResponse(tweet);
            // map user to userResponse
            tweetResponse.setUser(userMapper.userToUserTweetResponse(tweet.getUser()));
            // count LovedTweet
            tweetResponse.setTotalLove(loveTweetRepository.countByTweet(tweet));
            // count Saved
            tweetResponse.setTotalSaved(savedTweetRepository.countByTweet(tweet));
            // count Repost
            tweetResponse.setTotalRepost(repostTweetRepository.countByTweet(tweet));
            // count Comment
            tweetResponse.setTotalComment(commentRepository.countByTweet(tweet));

            listRs.add(tweetResponse);
        }
        return listRs;
    }


}
