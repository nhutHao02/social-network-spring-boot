package com.nhuthao02.social_network.services;

import com.nhuthao02.social_network.dtos.requests.notif.NotificationRequest;
import com.nhuthao02.social_network.dtos.responses.notif.NotificationResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface INotificationService {
    List<NotificationResponse> get(String id, Integer page, Integer limit);

    boolean add(NotificationRequest request);
}
