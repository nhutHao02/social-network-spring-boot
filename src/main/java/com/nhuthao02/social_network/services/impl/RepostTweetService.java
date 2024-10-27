package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.entities.LoveTweet;
import com.nhuthao02.social_network.entities.RepostTweet;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.TweetMapper;
import com.nhuthao02.social_network.mapper.UserMapper;
import com.nhuthao02.social_network.repositories.*;
import com.nhuthao02.social_network.services.IRepostTweetService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RepostTweetService implements IRepostTweetService {
    final
    RepostTweetRepository repository;

    final
    LoveTweetRepository loveTweetRepository;

    final
    SavedTweetRepository savedTweetRepository;

    final
    CommentRepository commentRepository;

    final
    UserRepository userRepository;

    final
    TweetRepository tweetRepository;

    final
    TweetMapper tweetMapper;

    final
    UserMapper userMapper;

    public RepostTweetService(RepostTweetRepository repository, LoveTweetRepository loveTweetRepository, SavedTweetRepository savedTweetRepository, CommentRepository commentRepository, UserRepository userRepository, TweetRepository tweetRepository, TweetMapper tweetMapper, UserMapper userMapper) {
        this.repository = repository;
        this.loveTweetRepository = loveTweetRepository;
        this.savedTweetRepository = savedTweetRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.tweetRepository = tweetRepository;
        this.tweetMapper = tweetMapper;
        this.userMapper = userMapper;
    }

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

    @Override
    public List<TweetResponse> getRepostTweet(String id, Integer page, Integer limit) {
        List<TweetResponse> listRs = new ArrayList<>();
        // get all saved tweet
        User user = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Page<RepostTweet> repostTweets = repository.findAllByUser(user, PageRequest.of(page, limit));
        for (RepostTweet repostTweet :
                repostTweets.getContent()) {
            // map tweet to tweetResponse
            TweetResponse tweetResponse = tweetMapper.tweetToTweetResponse(repostTweet.getTweet());
            // map user to userResponse
            tweetResponse.setUser(userMapper.userToUserTweetResponse(repostTweet.getTweet().getUser()));
            // count LovedTweet
            tweetResponse.setTotalLove(loveTweetRepository.countByTweet(repostTweet.getTweet()));
            // count Saved
            tweetResponse.setTotalSaved(savedTweetRepository.countByTweet(repostTweet.getTweet()));
            // count Repost
            tweetResponse.setTotalRepost(repository.countByTweet(repostTweet.getTweet()));
            // count Comment
            tweetResponse.setTotalComment(commentRepository.countByTweet(repostTweet.getTweet()));

            listRs.add(tweetResponse);
        }
        return listRs;
    }
}
