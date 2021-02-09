package com.sensedia.poc.message.controller.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ErrorResponseBean {
	
	@JsonProperty("mensagem")
	private String message;
	
	public ErrorResponseBean(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

