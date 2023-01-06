package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.CommentDto;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	private CommentRepository commentRepository;
	private ModelMapper modelMapper;
	private PostRepository postRepository;

	public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper,
			PostRepository postRepository) {

		this.commentRepository = commentRepository;
		this.modelMapper = modelMapper;
		this.postRepository = postRepository;
	}

	@Override
	public CommentDto createComment(CommentDto commentDto, long postId) {

		Comment comments = modelMapper.map(commentDto, Comment.class);
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
		comments.setPost(post);

		Comment newComment = this.commentRepository.save(comments);

		return this.modelMapper.map(newComment, CommentDto.class);

	}

	@Override
	public List<CommentDto> getAllCommentsByPostId(long postId) {
		List<Comment> comments = this.commentRepository.findByPostId(postId);
		return comments.stream().map(comment -> modelMapper.map(comment, CommentDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public CommentDto getCommentById(long postId, long commentId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!(post.getId() == (comment.getPost().getId()))) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belog to post");
		}
		return this.modelMapper.map(comment, CommentDto.class);
	}

	@Override
	public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!(post.getId() == (comment.getPost().getId()))) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
		}
		comment.setBody(commentDto.getBody());
		comment.setEmail(commentDto.getEmail());
		comment.setName(commentDto.getName());

		Comment comments = commentRepository.save(comment);
		return modelMapper.map(comments, CommentDto.class);
	}

	@Override
	public void deleteComment(long postId, long commentId) {
		Post post = this.postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "id", commentId));
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
		if (!(post.getId() == (comment.getPost().getId()))) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "comment does not belong to post");
		}

		commentRepository.delete(comment);

	}

}
