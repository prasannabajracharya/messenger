package org.prasanna.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.prasanna.messenger.database.DatabaseClass;
import org.prasanna.messenger.model.Message;

public class MessageService {
	
	private Map<Long, Message> messages = DatabaseClass.getMessages();	
	
	public MessageService() {
		messages.put(1L, new Message(1L, "Hello World", "Prasanna"));
		messages.put(2L, new Message(2L, "Hello Java", "Salauna"));
	}

	public List<Message> getAllMessages(){
		
		return new ArrayList<Message>(messages.values());
		
//		Message m1 = new Message(1L, "Hello World", "Prasanna");
//		Message m2 = new Message(2L, "Hello Java", "Salauna");
//		List<Message> msgList = new ArrayList<Message>();
//		msgList.add(m1);
//		msgList.add(m2);
//		return msgList;
	}
	
	public Message getMessage(long id){
		return messages.get(id);
	}
	
	public Message addMessage(Message message){
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message){
		if(message.getId() <= 0){
			return null;
		}
		messages.put(message.getId(), message);
		return message;	
	}
	
	public Message removeMessage(long id){
		return messages.remove(id);
	}
}