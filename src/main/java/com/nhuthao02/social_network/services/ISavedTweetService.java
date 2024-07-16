package com.nhuthao02.social_network.services;

public interface ISavedTweetService {
    boolean save(String userName, String tweetId);

    boolean deleteTweetSaved(String userName, String tweetId);
}
