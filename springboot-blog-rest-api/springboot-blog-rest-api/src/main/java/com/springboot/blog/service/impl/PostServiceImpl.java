package com.springboot.blog.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Post;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.service.PostService;

@Service
public class PostServiceImpl implements PostService {

	private PostRepository postRepository;
	private ModelMapper modelMapper;
	

	
	public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
		
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		// converted dto to entity
		Post post = modelMapper.map(postDto, Post.class);
		Post newPost = this.postRepository.save(post);
		PostDto dto = modelMapper.map(newPost, PostDto.class);
		return dto;
	}

	@Override
	public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {

		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
				: Sort.by(sortBy).descending();

		PageRequest pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Post> allPost = this.postRepository.findAll(pageable);
		// getContent page Object
		List<Post> posts = allPost.getContent();
		// converted entity to dto
		List<PostDto> listPost = posts.stream().map(post->modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(listPost);
		postResponse.setPageNo(allPost.getNumber());
		postResponse.setPageSize(allPost.getSize());
		postResponse.setTotalElements(allPost.getTotalElements());
		postResponse.setTotalPages(allPost.getTotalPages());
		postResponse.setLast(allPost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(long id) {
		Post newPost = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return modelMapper.map(newPost,PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		Post newPost = this.postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "Id", id));

		newPost.setContent(postDto.getContent());
		newPost.setDescription(postDto.getDescription());
		newPost.setTitle(postDto.getTitle());
		
		Post posts = this.postRepository.save(newPost);

		return modelMapper.map(posts, PostDto.class);
	}

	@Override
	public void deletePost(long id) {
		Post post = this.postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		this.postRepository.delete(post);
	}

//	// converted entity into dto
//	private PostDto entitytoDto(Post post) {
//		PostDto postDto = new PostDto();
//		postDto.setContent(post.getContent());
//		postDto.setDescription(post.getDescription());
//		postDto.setTitle(post.getTitle());
//		postDto.setId(post.getId());
//
//		return postDto;
//
//	}

//	// Converted dto to entity
//	private Post dtoToEntity(PostDto postDto) {
//		Post post = new Post();
//		post.setContent(postDto.getContent());
//		post.setDescription(postDto.getDescription());
//		post.setTitle(postDto.getTitle());
//		// post.setId(postDto.getId());
//
//		return post;
//
//	}

}
