package org.prasanna.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.prasanna.messenger.database.DatabaseClass;
import org.prasanna.messenger.model.Comment;
import org.prasanna.messenger.model.Message;

public class CommentService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId){
		Map<Long, Comment> commentList = messages.get(messageId).getComments();
		return new ArrayList<Comment>(commentList.values());
	}
	
	public Comment getComment(long messageId, long commentId){
		Map<Long, Comment> commentList = messages.get(messageId).getComments();
		return commentList.get(commentId);
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
