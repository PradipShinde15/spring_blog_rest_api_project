package com.springboot.blog.payload;

import java.util.Set;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;



@Data
public class PostDto {
	
	private long id;
	
	@NotEmpty(message = "title should not be null")
	@Size(min = 2,message = "Post title should have atleast 2 charectors")
	private String title;
	
	@NotEmpty
	@Size(min = 10,message = "descrption title should have atleast 10 charectors")
	private String description;
	
    @NotEmpty
	private String content;
    
	private Set<CommentDto> comments;
	

}
