package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.comment.AddCommentRequest;
import com.nhuthao02.social_network.dtos.responses.comment.CommentResponse;

import java.util.List;

public interface ICommentService {
    boolean addComment(AddCommentRequest request);

    boolean updateComment(String commentId, AddCommentRequest request);

    boolean deleteComment(String commentId);

    List<CommentResponse> getComments(String tweetId, Integer page, Integer limit);


}
