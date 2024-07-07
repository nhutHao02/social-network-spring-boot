package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Entity(name = "recentMessages")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RecentMessage extends BaseEntity {
    @NotNull
    String uuidReceiver;
    String content;

    // relationship
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recentMessage_user_id")
    User user;
}
