package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.requests.message.GetMessageRequest;
import com.nhuthao02.social_network.dtos.requests.message.MessageRequest;
import com.nhuthao02.social_network.dtos.responses.message.MessageResponse;
import com.nhuthao02.social_network.entities.Message;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.MessageMapper;
import com.nhuthao02.social_network.repositories.MessageRepository;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.IMessageService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MessageService implements IMessageService {
    final
    MessageRepository repository;

    final
    UserRepository userRepository;

    final
    MessageMapper mapper;

    public MessageService(MessageRepository repository, UserRepository userRepository, MessageMapper mapper) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean add(MessageRequest request) {
        if (!userRepository.existsById(request.getUuidSender())) throw new AppException(ErrorCode.USER_NOT_FOUND);
        if (!userRepository.existsById(request.getUuidReceiver())) throw new AppException(ErrorCode.USER_NOT_FOUND);

        Message message = mapper.toMessage(request);
        repository.save(message);
        return true;
    }

    @Override
    public List<MessageResponse> get(GetMessageRequest request, Integer page, Integer limit) {
        if (!userRepository.existsById(request.getUuidSender())) throw new AppException(ErrorCode.USER_NOT_FOUND);
        if (!userRepository.existsById(request.getUuidReceiver())) throw new AppException(ErrorCode.USER_NOT_FOUND);

        Page<Message> messages =
                repository.findAllByUuidSenderAndUuidReceiverOrUuidReceiverAndUuidSenderOrderByCreateAtDesc(
                        request.getUuidSender(),
                        request.getUuidReceiver(),
                        PageRequest.of(page, limit));
        return mapper.messageToMessageResponse(messages.getContent());
    }

    @Override
    public boolean delete(String id) {
        Message message = repository.findById(id).orElseThrow(() -> new AppException(ErrorCode.MESSAGE_NOT_FOUND));
        repository.delete(message);
        return true;
    }
}
