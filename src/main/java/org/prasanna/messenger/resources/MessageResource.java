package org.prasanna.messenger.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.prasanna.messenger.model.Message;
import org.prasanna.messenger.service.MessageService;

@Path("/messages")
//instead of adding @Consumes and @Produces in each method; place it above class
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();
	
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(){
		
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long id){		
		return messageService.getMessage(id);
	}
	
	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
	public Message addMessage(Message message){
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message){
		message.setId(id);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id){
		System.out.println("Message deleted:"+messageService.getMessage(id));
		messageService.removeMessage(id);
		
		return;
	}
	
}
