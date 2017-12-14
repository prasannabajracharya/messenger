package org.prasanna.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.prasanna.messenger.database.DatabaseClass;
import org.prasanna.messenger.model.Comment;
import org.prasanna.messenger.model.ErrorMessage;
import org.prasanna.messenger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId){
		Map<Long, Comment> commentList = messages.get(messageId).getComments();
		return new ArrayList<Comment>(commentList.values());
	}
	
	public Comment getComment(long messageId, long commentId){
		Message message = messages.get(messageId);
		
		ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "http://www.javadoc.com");
		Response response = Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		
		if(message == null){
			throw new WebApplicationException(response); 
			//WebApplicationException is not preferred because code related to view like response is in service layer. Service layer should actually be only for business logic
			//better to place this code in Resource file or ExceptionMapper is better alternative where they are at separate file
		}
				
		Map<Long, Comment> commentList = message.getComments();
		Comment comment = commentList.get(commentId);
		if(comment == null){
			throw new NotFoundException(response); //another alternative to WebApplicationException where not need to specify StatusCode
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment){
		Map<Long, Comment> commentList = messages.get(messageId).getComments();
		comment.setId(commentList.size()+1);
		commentList.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment){
		Map<Long, Comment> commentList = messages.get(messageId).getComments();
		if(comment.getId() <= 0 ){
			return null;
		}
		commentList.put(comment.getId(), comment);
		return comment;
	}
	
	public void deleteComment(long messageId, long commentId){
		Map<Long, Comment> commentList = messages.get(messageId).getComments();
		commentList.remove(commentId);
		return;
	}
}
