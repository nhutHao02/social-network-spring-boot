package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.comment.AddCommentRequest;
import com.nhuthao02.social_network.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment addCommentRequestToComment(AddCommentRequest request);

    void updateComment(@MappingTarget Comment comment, AddCommentRequest request);
}
