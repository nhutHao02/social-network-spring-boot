package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.RecentMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentMessageRepository extends JpaRepository<RecentMessage, String> {
}
