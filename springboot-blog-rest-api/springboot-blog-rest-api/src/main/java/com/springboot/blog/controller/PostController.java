package com.springboot.blog.controller;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.PostDto;
import com.springboot.blog.payload.PostDto2;
import com.springboot.blog.payload.PostResponse;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class PostController {
	
	
	private PostService postService;

	public PostController(PostService postService) {
		this.postService = postService;
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto){
		return new ResponseEntity<PostDto>(postService.createPost(postDto),HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/posts")
	public PostResponse getAllPost(@RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
			@RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false) int pageSize,
			@RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORTBY,required = false) String sortBy,
			@RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false) String sortDir){
		return postService.getAllPost(pageNo, pageSize,sortBy,sortDir);
	}
	// versioning through URI
	@GetMapping("/posts/{id}")
	public ResponseEntity<PostDto> getPostByIdV1(@PathVariable long id){
		return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
		
	}
	//// versioning through URI
//	@GetMapping("/api/v2/posts/{id}")
//	public ResponseEntity<PostDto2> getPostByIdV2(@PathVariable long id){
//		
//	    PostDto postDto = postService.getPostById(id);
//		
//		PostDto2 postDto2=new PostDto2();
//		postDto2.setContent(postDto.getContent());
//		postDto2.setDescription(postDto.getDescription());
//		postDto2.setTitle(postDto.getTitle());
//		postDto2.setComments(postDto.getComments());
//		postDto2.setId(postDto.getId());
//		List<String> tags=new ArrayList<String>();
//		tags.add("java");
//		tags.add("mysql");
//		tags.add("aws");
//		
//		postDto2.setTags(tags);
//		return ResponseEntity.ok(postDto2);
//		
//		
//	//	return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
//		
//	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/posts/{id}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable long id){
		return new ResponseEntity<PostDto>(postService.updatePost(postDto,id),HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/posts/{id}")
	public ResponseEntity<String> deletePost(@PathVariable long id){
		this.postService.deletePost(id);
		
		return new ResponseEntity<String>("Post deleted successfully",HttpStatus.OK);
		
	}
	
	
	
//	// versioning through query parameter
//		@GetMapping(value="/api/posts/{id}",params = "version=1")
//		public ResponseEntity<PostDto> getPostByIdV1(@PathVariable long id){
//			return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
//			
//		}
//		//// versioning through query parameter
//		@GetMapping(value="/api/posts/{id}",params = "version=2")
//		public ResponseEntity<PostDto2> getPostByIdV2(@PathVariable long id){
//			
//		    PostDto postDto = postService.getPostById(id);
//			
//			PostDto2 postDto2=new PostDto2();
//			postDto2.setContent(postDto.getContent());
//			postDto2.setDescription(postDto.getDescription());
//			postDto2.setTitle(postDto.getTitle());
//			postDto2.setComments(postDto.getComments());
//			postDto2.setId(postDto.getId());
//			List<String> tags=new ArrayList<String>();
//			tags.add("java");
//			tags.add("mysql");
//			tags.add("aws");
//			
//			postDto2.setTags(tags);
//			return ResponseEntity.ok(postDto2);
//			
//			
//		//	return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
//			
//		}
	
////	 versioning through custom headers
//		@GetMapping(value="/api/posts/{id}",headers = "X-API-VERSION=1")
//		public ResponseEntity<PostDto> getPostByIdV1(@PathVariable long id){
//			return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
//			
//		}
//		//// versioning through custom headers
//		@GetMapping(value="/api/posts/{id}",headers = "X-API-VERSION=2")
//		public ResponseEntity<PostDto2> getPostByIdV2(@PathVariable long id){
//			
//		    PostDto postDto = postService.getPostById(id);
//			
//			PostDto2 postDto2=new PostDto2();
//			postDto2.setContent(postDto.getContent());
//			postDto2.setDescription(postDto.getDescription());
//			postDto2.setTitle(postDto.getTitle());
//			postDto2.setComments(postDto.getComments());
//			postDto2.setId(postDto.getId());
//			List<String> tags=new ArrayList<String>();
//			tags.add("java");
//			tags.add("mysql");
//			tags.add("aws");
//			
//			postDto2.setTags(tags);
//			return ResponseEntity.ok(postDto2);
//			
//			
//		//	return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
//			
//		}
	
	//versioning through content negotiation
//	@GetMapping(value="/api/posts/{id}",produces = "application/vnd.pradip.v1+json")
//	public ResponseEntity<PostDto> getPostByIdV1(@PathVariable long id){
//		return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
//		
//	}
//	//// versioning through content negotiation
//	@GetMapping(value="/api/posts/{id}",produces = "application/vnd.pradip.v2+json")
//	public ResponseEntity<PostDto2> getPostByIdV2(@PathVariable long id){
//		
//	    PostDto postDto = postService.getPostById(id);
//		
//		PostDto2 postDto2=new PostDto2();
//		postDto2.setContent(postDto.getContent());
//		postDto2.setDescription(postDto.getDescription());
//		postDto2.setTitle(postDto.getTitle());
//		postDto2.setComments(postDto.getComments());
//		postDto2.setId(postDto.getId());
//		List<String> tags=new ArrayList<String>();
//		tags.add("java");
//		tags.add("mysql");
//		tags.add("aws");
//		
//		postDto2.setTags(tags);
//		return ResponseEntity.ok(postDto2);
//		
//		
//	//	return new ResponseEntity<PostDto>(postService.getPostById(id),HttpStatus.OK);
//		
//	}

	
	

	
	
	
	

}
