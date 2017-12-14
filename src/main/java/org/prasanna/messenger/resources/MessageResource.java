package org.prasanna.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.prasanna.messenger.model.Message;
import org.prasanna.messenger.resources.beans.MessageFilterBean;
import org.prasanna.messenger.service.MessageService;

@Path("/messages")
//instead of adding @Consumes and @Produces in each method; place it above class
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();
	
	@GET
	//@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getMessages(
//			@QueryParam("year") int year,
//			@QueryParam("start") int start,
//			@QueryParam("size") int size
			
			@BeanParam MessageFilterBean messageFilterBean
			){
		if(messageFilterBean.getYear() > 0){
			return messageService.getAllMessagesForYear(messageFilterBean.getYear());
		}
		if(messageFilterBean.getStart() > 0 && messageFilterBean.getSize() > 0){
			return messageService.getAllMessagesPaginated(messageFilterBean.getStart(), messageFilterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Path("/{messageId}")
	//@Produces(MediaType.APPLICATION_JSON)
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo){	
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "profile");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder() // http://localhost:8080/messenger/webapi/
				.path(MessageResource.class)	// messages/
				//.path(CommentResource.class)	//does not work because Comment is sub-resource
				.path(MessageResource.class, "getCommentResource") // not a good idea to hardcode methodname but it is how JAX-RS implementation is. There is builtin class Link but its full of bug
				.path(CommentResource.class) // not required now because uri is just "/" but may change later; just good programming practice
				.resolveTemplate("messageId", message.getId()) // replaces dynamic {messageId} in uri with actual messageId
				.build();
			return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
				.path(ProfileResource.class)
				.path(message.getAuthor())
				.build()
				.toString();
			return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
			.path(MessageResource.class)
			.path(Long.toString(message.getId()))
			.build()
			.toString();
		return uri;
	}
	
	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
	public Response addMessage(Message message, @Context UriInfo uriInfo){
		Message newMessage = messageService.addMessage(message);
		String newId = String.valueOf(newMessage.getId());
		
		URI uri = uriInfo.getAbsolutePathBuilder() // Builder converts uri path to string
				.path(newId) // appends "/" and newID
				.build();
		return Response.created(uri) // returns HTTP Statuscode as 201 created instead of 200 status ok
				.entity(newMessage)
				.build(); //Builder pattern
		//return messageService.addMessage(message);
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
		messageService.removeMessage(id);
		
		return;
	}
	
	//comment is a sub-resource inside message resource
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
	
}
