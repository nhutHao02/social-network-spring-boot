package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.entities.SaveTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.TweetMapper;
import com.nhuthao02.social_network.mapper.UserMapper;
import com.nhuthao02.social_network.repositories.*;
import com.nhuthao02.social_network.services.ISavedTweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SavedTweetService implements ISavedTweetService {
    @Autowired
    SavedTweetRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    LoveTweetRepository loveTweetRepository;

    @Autowired
    RepostTweetRepository repostTweetRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TweetMapper tweetMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public boolean save(String userName, String tweetId) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new AppException(ErrorCode.TWEET_NOT_FOUND));
        repository.save(SaveTweet.builder().user(user).tweet(tweet).build());
        return true;
    }

    @Override
    public boolean deleteTweetSaved(String userName, String tweetId) {
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Tweet tweet = tweetRepository.findById(tweetId).orElseThrow(() -> new AppException(ErrorCode.TWEET_NOT_FOUND));
        Optional<SaveTweet> saveTweet = repository.findSaveTweetByUserAndTweet(user, tweet);
        if (saveTweet.isEmpty()) {
            throw new AppException(ErrorCode.TWEET_REPOSTED_NOT_FOUND);
        }
        repository.delete(saveTweet.get());
        return true;
    }

    @Override
    public List<TweetResponse> getSavedTweet(String userName, Integer page, Integer limit) {
        List<TweetResponse> listRs = new ArrayList<>();
        // get all saved tweet
        User user = userRepository.findByUserName(userName).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Page<SaveTweet> saveTweets = repository.findAllByUser(user, PageRequest.of(page, limit));
        for (SaveTweet saveTweet :
                saveTweets.getContent()) {
            // map tweet to tweetResponse
            TweetResponse tweetResponse = tweetMapper.tweetToTweetResponse(saveTweet.getTweet());
            // map user to userResponse
            tweetResponse.setUser(userMapper.userToUserTweetResponse(saveTweet.getTweet().getUser()));
            // count LovedTweet
            tweetResponse.setTotalLove(loveTweetRepository.countByTweet(saveTweet.getTweet()));
            // count Saved
            tweetResponse.setTotalSaved(repository.countByTweet(saveTweet.getTweet()));
            // count Repost
            tweetResponse.setTotalRepost(repostTweetRepository.countByTweet(saveTweet.getTweet()));
            // count Comment
            tweetResponse.setTotalComment(commentRepository.countByTweet(saveTweet.getTweet()));

            listRs.add(tweetResponse);
        }
        return listRs;
    }
}
