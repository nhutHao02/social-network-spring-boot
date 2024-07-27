package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity(name = "messages")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Message extends BaseEntity {
    String uuidSender;
    String uuidReceiver;
    String content;
    String urlImage;
    String urlVideo;
}
