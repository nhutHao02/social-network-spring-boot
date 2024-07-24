package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;

import java.util.List;

public interface IRepostTweetService {
    boolean repost(String userName, String tweetId);

    boolean deleteTweetReposted(String userName, String tweetId);

    List<TweetResponse> getRepostTweet(String userName, Integer page, Integer limit);

}
