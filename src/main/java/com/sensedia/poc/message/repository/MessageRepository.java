package com.sensedia.poc.message.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sensedia.poc.message.repository.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {
}
