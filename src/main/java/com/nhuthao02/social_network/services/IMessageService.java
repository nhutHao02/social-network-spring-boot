package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.message.GetMessageRequest;
import com.nhuthao02.social_network.dtos.requests.message.MessageRequest;
import com.nhuthao02.social_network.dtos.responses.message.MessageResponse;

import java.util.List;

public interface IMessageService {
    boolean add(MessageRequest request);

    List<MessageResponse> get(GetMessageRequest request, Integer page, Integer limit);

    boolean delete(String id);
}
