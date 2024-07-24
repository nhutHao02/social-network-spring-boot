package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.tweet.PostTweetRequest;
import com.nhuthao02.social_network.dtos.requests.tweet.UpdateTweetRequest;
import com.nhuthao02.social_network.dtos.responses.tweet.TweetResponse;
import com.nhuthao02.social_network.entities.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TweetMapper {
    Tweet requestToTweet(PostTweetRequest request);

    void updateTweet(@MappingTarget Tweet tweet, UpdateTweetRequest request);

    @Mapping(source = "user", target = "user")
    List<TweetResponse> listToTweets(List<Tweet> tweets);

    TweetResponse tweetToTweetResponse(Tweet tweet);
}
