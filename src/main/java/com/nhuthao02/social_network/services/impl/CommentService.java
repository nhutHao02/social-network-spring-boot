package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.requests.comment.AddCommentRequest;
import com.nhuthao02.social_network.entities.Comment;
import com.nhuthao02.social_network.entities.Tweet;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.CommentMapper;
import com.nhuthao02.social_network.repositories.CommentRepository;
import com.nhuthao02.social_network.repositories.TweetRepository;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.ICommentService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentService implements ICommentService {
    @Autowired
    CommentRepository repository;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CommentMapper mapper;

    @Override
    public boolean addComment(AddCommentRequest request) {
        if (!userRepository.existsById(request.getUuidCommenter())) throw new AppException(ErrorCode.USER_NOT_FOUND);
        Tweet tweet = tweetRepository.findById(request.getTweetId()).orElseThrow(() -> new AppException(ErrorCode.TWEET_NOT_FOUND));

        Comment comment = mapper.addCommentRequestToComment(request);
        comment.setTweet(tweet);
        repository.save(comment);
        return true;
    }

    @Override
    public boolean updateComment(String commentId, AddCommentRequest request) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));

        mapper.updateComment(comment, request);

        repository.save(comment);
        return true;
    }

    @Override
    public boolean deleteComment(String commentId) {
        Comment comment = repository.findById(commentId).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
        repository.delete(comment);
        return true;
    }
}
