package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.comment.AddCommentRequest;

public interface ICommentService {
    boolean addComment(AddCommentRequest request);

    boolean updateComment(String commentId, AddCommentRequest request);

    boolean deleteComment(String commentId);


}
