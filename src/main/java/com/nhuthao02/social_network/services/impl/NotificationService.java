package com.nhuthao02.social_network.services.impl;

import com.nhuthao02.social_network.dtos.requests.notif.NotificationRequest;
import com.nhuthao02.social_network.dtos.responses.notif.NotificationResponse;
import com.nhuthao02.social_network.entities.Notification;
import com.nhuthao02.social_network.entities.User;
import com.nhuthao02.social_network.exception.AppException;
import com.nhuthao02.social_network.exception.ErrorCode;
import com.nhuthao02.social_network.mapper.NotificationMapper;
import com.nhuthao02.social_network.repositories.NotificationRepository;
import com.nhuthao02.social_network.repositories.UserRepository;
import com.nhuthao02.social_network.services.INotificationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationService implements INotificationService {
    @Autowired
    NotificationRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationMapper mapper;

    @Override
    public List<NotificationResponse> get(String id, Integer page, Integer limit) {
        if (!userRepository.existsById(id)) throw new AppException(ErrorCode.USER_NOT_FOUND);
        Page<Notification> notifications = repository.findAllByUuidReceiver(id, PageRequest.of(page, limit));
        return mapper.notifToNotifResponse(notifications.getContent());
    }

    @Override
    public boolean add(NotificationRequest request) {
        User user = userRepository.findById(request.getUuidSender()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Notification notification = mapper.requestToNotification(request);
        notification.setUser(user);
        repository.save(notification);
        return true;
    }
}
