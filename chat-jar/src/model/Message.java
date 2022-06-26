package model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import util.DateDeserializer;
import util.DateSerializer;

public class Message implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private User msgSender;
	private User msgReceiver;
	@JsonDeserialize(using = DateDeserializer.class)
	@JsonSerialize(using = DateSerializer.class)
	private LocalDateTime msgCreated;
	private String msgSubject;
	private String msgContent;
	
	public Message() {
		
	}
	
	public Message(User sender, User receiver, LocalDateTime created, String subject, String content) {
		super();
		this.msgSender = sender;
		this.msgReceiver = receiver;
		this.msgCreated = created;
		this.msgSubject = subject;
		this.msgContent = content;
	}

	public User getSender() {
		return msgSender;
	}

	public User getReceiver() {
		return msgReceiver;
	}

	public LocalDateTime getCreated() {
		return msgCreated;
	}

	public String getSubject() {
		return msgSubject;
	}

	public String getContent() {
		return msgContent;
	}

	@Override
	public boolean equals(Object obj) {
		Message message = (Message)obj;
		return msgSender.equals(message.msgSender) &&
				msgReceiver.equals(message.msgReceiver) &&
				msgCreated.equals(message.msgCreated) &&
				msgSubject.equals(message.msgSubject) &&
				msgContent.equals(message.msgContent);
	}
	
	@Override
	public String toString() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			return super.toString();
		}
	}
}
