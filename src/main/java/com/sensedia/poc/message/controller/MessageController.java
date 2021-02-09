package com.sensedia.poc.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sensedia.poc.message.controller.bean.MessageBodyRequestBean;
import com.sensedia.poc.message.controller.bean.ResultResponseBean;
import com.sensedia.poc.message.controller.bean.Status;
import com.sensedia.poc.message.service.MessageService;

@Controller
public class MessageController {
	
	@Autowired
	private MessageService messageService;

	@GetMapping("/message")
	public ResponseEntity<ResultResponseBean> getMessages() {
		try {
			final ResultResponseBean result = new ResultResponseBean(messageService.getMessages(), Status.success);
			return new ResponseEntity<ResultResponseBean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponseBean>(new ResultResponseBean("Ocorreu um erro na solicitação", Status.fail), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/message/{id}")
	public ResponseEntity<ResultResponseBean> getMessage(@PathVariable("id") Integer id) {
		try {
			final ResultResponseBean result = new ResultResponseBean(messageService.getMessage(id), Status.success);
			return new ResponseEntity<ResultResponseBean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponseBean>(new ResultResponseBean("Ocorreu um erro na solicitação", Status.fail), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/message/{id}")
	public ResponseEntity<ResultResponseBean> deleteMessage(@PathVariable("id") Integer id) {
		try {
			final ResultResponseBean result = new ResultResponseBean(messageService.deleteMessage(id), Status.success);
			return new ResponseEntity<ResultResponseBean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponseBean>(new ResultResponseBean("Ocorreu um erro na solicitação", Status.fail), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/message")
	public ResponseEntity<ResultResponseBean> createMessage(@RequestBody MessageBodyRequestBean message) {
		try {
			final ResultResponseBean result = new ResultResponseBean(messageService.createMessage(message.getText()), Status.success);
			return new ResponseEntity<ResultResponseBean>(result, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<ResultResponseBean>(new ResultResponseBean("Ocorreu um erro na solicitação", Status.fail), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
