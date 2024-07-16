package com.nhuthao02.social_network.services;

public interface IRepostTweetService {
    boolean repost(String userName, String tweetId);

    boolean deleteTweetReposted(String userName, String tweetId);

}
