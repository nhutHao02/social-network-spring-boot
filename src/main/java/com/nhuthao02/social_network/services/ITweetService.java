package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.tweet.PostTweetRequest;
import com.nhuthao02.social_network.dtos.requests.tweet.UpdateTweetRequest;
import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;

import java.util.List;

public interface ITweetService {
    boolean addTweet(PostTweetRequest request);

    boolean updateTweet(UpdateTweetRequest request);

    boolean deleteTweet(String id);

    List<TweetResponse> getTweets(Integer page, Integer limit);

    List<TweetResponse> getTweetsByUserID(String userID, Integer page, Integer limit);
}
