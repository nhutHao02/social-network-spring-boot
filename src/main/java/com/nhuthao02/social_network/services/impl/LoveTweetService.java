package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.entities.LoveTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.TweetMapper;
import com.nhuthao02.social_network.mapper.UserMapper;
import com.nhuthao02.social_network.repositories.*;
import com.nhuthao02.social_network.services.ILoveTweetService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    SavedTweetRepository savedTweetRepository;

    @Autowired
    RepostTweetRepository repostTweetRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    LoveTweetRepository repository;

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserMapper userMapper;

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
        List<TweetResponse> listRs = new ArrayList<>();
        // get all saved tweet
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Page<LoveTweet> loveTweets = repository.findAllByUser(user, PageRequest.of(page, limit));
        for (LoveTweet loveTweet :
                loveTweets.getContent()) {
            // map tweet to tweetResponse
            TweetResponse tweetResponse = tweetMapper.tweetToTweetResponse(loveTweet.getTweet());
            // map user to userResponse
            tweetResponse.setUser(userMapper.userToUserTweetResponse(loveTweet.getTweet().getUser()));
            // count LovedTweet
            tweetResponse.setTotalLove(repository.countByTweet(loveTweet.getTweet()));
            // count Saved
            tweetResponse.setTotalSaved(savedTweetRepository.countByTweet(loveTweet.getTweet()));
            // count Repost
            tweetResponse.setTotalRepost(repostTweetRepository.countByTweet(loveTweet.getTweet()));
            // count Comment
            tweetResponse.setTotalComment(commentRepository.countByTweet(loveTweet.getTweet()));

            listRs.add(tweetResponse);
        }
        return listRs;
    }

}
