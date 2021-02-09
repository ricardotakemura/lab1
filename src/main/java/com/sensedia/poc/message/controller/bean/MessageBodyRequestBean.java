package com.sensedia.poc.message.controller.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageBodyRequestBean {

	@JsonProperty("texto")
	private String text;

	public MessageBodyRequestBean(String text) {
		this.text = text;
	}

	public MessageBodyRequestBean() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
