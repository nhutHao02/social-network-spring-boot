package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;

import java.util.List;

public interface ISavedTweetService {
    boolean save(String userName, String tweetId);

    boolean deleteTweetSaved(String userName, String tweetId);

    List<TweetResponse> getSavedTweet(String id, Integer page, Integer limit);
}
