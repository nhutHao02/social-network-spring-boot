package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "notifications")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {
    String content;
    String uuidSender;

    //relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_user_id")
    User user;
}
