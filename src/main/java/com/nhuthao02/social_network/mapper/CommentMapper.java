package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.comment.AddCommentRequest;
import com.nhuthao02.social_network.dtos.responses.comment.CommentResponse;
import com.nhuthao02.social_network.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment addCommentRequestToComment(AddCommentRequest request);

    void updateComment(@MappingTarget Comment comment, AddCommentRequest request);

    List<CommentResponse> commentsToCommentResponses(List<Comment> comments);
}
