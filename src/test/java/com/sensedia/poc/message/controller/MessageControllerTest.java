package com.sensedia.poc.message.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sensedia.poc.message.controller.bean.MessageBodyRequestBean;
import com.sensedia.poc.message.controller.bean.ResultResponseBean;
import com.sensedia.poc.message.repository.MessageRepository;
import com.sensedia.poc.message.repository.model.Message;
import com.sensedia.poc.message.service.impl.MessageServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {MessageControllerTest.class, MessageServiceImpl.class, MessageController.class})
public class MessageControllerTest {
	
	@Autowired
	private MessageController messageController;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Bean
	public MessageRepository messageRepository() {
		return Mockito.mock(MessageRepository.class);
	}

	@Test
	public void testGetMessages() {	
		List<Message> answer = Collections.singletonList(new Message(1, "Hello world"));
		Mockito.when(messageRepository.findAll()).thenReturn(answer);
		ResponseEntity<ResultResponseBean> result = messageController.getMessages();
		Assert.assertEquals(result.getBody().getResult(), answer);
	}
	
	@Test
	public void testGetMessage() {	
		Message answer = new Message(1, "Hello world");
		Mockito.when(messageRepository.findById(1)).thenReturn(Optional.of(answer));
		ResponseEntity<ResultResponseBean> result = messageController.getMessage(1);
		Assert.assertEquals(result.getBody().getResult(), answer);
	}

	@Test
	public void testPostMessage() {	
		Message answer = new Message(1, "Hello world");
		Mockito.when(messageRepository.save(new Message(null, "Hello world"))).thenReturn(answer);
		ResponseEntity<ResultResponseBean> result = messageController.createMessage(new MessageBodyRequestBean("Hello world"));
		Assert.assertEquals(result.getBody().getResult(), answer);
	}

	@Test
	public void testDeleteMessage() {	
		Message answer = new Message(1, "Hello world");
		Mockito.when(messageRepository.findById(1)).thenReturn(Optional.of(answer));		
		ResponseEntity<ResultResponseBean> result = messageController.deleteMessage(1);
		Mockito.verify(messageRepository, Mockito.atLeastOnce()).delete(answer);
		Assert.assertEquals(result.getBody().getResult(), answer);
	}

}
