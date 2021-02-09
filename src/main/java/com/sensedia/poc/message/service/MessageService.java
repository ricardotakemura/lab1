package com.sensedia.poc.message.service;

import java.util.List;

import com.sensedia.poc.message.repository.model.Message;

public interface MessageService {

	List<Message> getMessages();

	Message getMessage(Integer id);

	Message createMessage(String text);

	Message deleteMessage(Integer id);

}
