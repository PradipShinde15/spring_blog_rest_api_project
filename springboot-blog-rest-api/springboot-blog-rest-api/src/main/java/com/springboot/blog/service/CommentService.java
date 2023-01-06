package com.springboot.blog.service;

import java.util.List;

import com.springboot.blog.payload.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,long postId);
	
	List<CommentDto> getAllCommentsByPostId(long postId);
	
	CommentDto getCommentById(long postId,long commentId);
	
	
	CommentDto updateComment(long postId,long commentId,CommentDto commentDto);

	void deleteComment(long postId,long commentId);
	
	

}
