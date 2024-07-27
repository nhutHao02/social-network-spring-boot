package com.nhuthao02.social_network.repositories;

import com.nhuthao02.social_network.entities.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, String> {
    @Query("SELECT m FROM messages m WHERE (m.uuidSender = :uuidSender AND m.uuidReceiver = :uuidReceiver) OR (m.uuidSender = :uuidReceiver AND m.uuidReceiver = :uuidSender) ORDER BY m.createAt DESC")
    Page<Message> findAllByUuidSenderAndUuidReceiverOrUuidReceiverAndUuidSenderOrderByCreateAtDesc(String uuidSender,
                                                                                                   String uuidReceiver,
                                                                                                   Pageable pageable);
}

