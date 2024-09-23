package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;

import java.util.List;

public interface ILoveTweetService {
    boolean loveTweet(String userName, String tweetId);

    boolean deleteTweetLoved(String userName, String tweetId);

    List<TweetResponse> getTweetLoved(String id, Integer page, Integer limit);

}

