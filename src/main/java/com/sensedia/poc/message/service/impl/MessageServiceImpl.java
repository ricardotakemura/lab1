package com.sensedia.poc.message.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.sensedia.poc.message.repository.MessageRepository;
import com.sensedia.poc.message.repository.model.Message;
import com.sensedia.poc.message.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messageRepository;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Message> getMessages() {
		return messageRepository.findAll();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Message getMessage(Integer id) {
		return messageRepository.findById(id).orElseThrow();
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Message createMessage(String text) {
		Message message = new Message();
		message.setText(text);
		return messageRepository.save(message);		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Message deleteMessage(Integer id) {
		Message message = messageRepository.findById(id).orElseThrow();
		messageRepository.delete(message);
		return message;
	}

}
