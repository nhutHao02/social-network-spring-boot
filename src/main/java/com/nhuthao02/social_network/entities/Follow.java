package com.nhuthao02.social_network.entities;

import com.nhuthao02.social_network.entities.baseInterface.BaseEntity;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

@Entity(name = "follows")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Follow extends BaseEntity {
    @NotNull
    String uuidUserFollower;
    @NotNull
    String uuidUserFollowing;
}
