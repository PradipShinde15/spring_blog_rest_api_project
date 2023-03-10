package com.springboot.blog.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.service.CommentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
	
	private CommentService commentService;

	public CommentController(CommentService commentService) {
		
		this.commentService = commentService;
	}
	@PostMapping("/posts/{postId}/comments")
	public ResponseEntity<CommentDto> createComment ( @PathVariable long postId, @Valid  @RequestBody CommentDto commentDto){
		
		return new ResponseEntity<CommentDto>(this.commentService.createComment(commentDto, postId),HttpStatus.CREATED);
		
	}
	@GetMapping("/posts/{postId}/comments")
	public List<CommentDto> getCommentByPostId(@PathVariable long postId){
		return this.commentService.getAllCommentsByPostId(postId);
		
	}
	@GetMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<CommentDto> getCommentById(@PathVariable long postId, @PathVariable long commentId){
		
		CommentDto comment = commentService.getCommentById(postId, commentId);
		return new ResponseEntity<CommentDto>(comment,HttpStatus.OK);
		
	}
	
	@PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> UpdateCommentId(@PathVariable long postId, @PathVariable long commentId,@Valid  @RequestBody CommentDto commentDto){
		
		CommentDto comment = commentService.updateComment(postId, commentId, commentDto);
		return new ResponseEntity<CommentDto>(comment,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public ResponseEntity<String> deleteCommentById(@PathVariable long postId, @PathVariable long commentId){
		
		commentService.deleteComment(postId, commentId);
		return new ResponseEntity<String>("Comment deleted successfully",HttpStatus.OK);
		
	}
	

}
