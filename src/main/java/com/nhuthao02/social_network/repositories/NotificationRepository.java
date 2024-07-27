package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, String> {
    Page<Notification> findAllByUuidReceiver(String id, Pageable pageable);
}
