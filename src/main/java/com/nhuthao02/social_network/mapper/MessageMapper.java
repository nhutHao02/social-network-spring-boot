package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.message.MessageRequest;
import com.nhuthao02.social_network.dtos.responses.message.MessageResponse;
import com.nhuthao02.social_network.entities.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    Message toMessage(MessageRequest request);

    @Mapping(target = "uuidSender", source = "uuidSender")
    @Mapping(target = "id", source = "id")
    List<MessageResponse> messageToMessageResponse(List<Message> message);
}
