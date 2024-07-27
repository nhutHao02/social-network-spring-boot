package com.nhuthao02.social_network.mapper;

import com.nhuthao02.social_network.dtos.requests.notif.NotificationRequest;
import com.nhuthao02.social_network.dtos.responses.notif.NotificationResponse;
import com.nhuthao02.social_network.entities.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface NotificationMapper {
    @Mapping(source = "content", target = "content")
    List<NotificationResponse> notifToNotifResponse(List<Notification> notifications);

    Notification requestToNotification(NotificationRequest request);
}
