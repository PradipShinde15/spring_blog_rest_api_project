package com.springboot.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

//import org.hibernate.validator.constraints.Email;
//
//import org.hibernate.validator.constraints.NotEmpty;
//
//import com.springboot.blog.entity.Post;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	
	private long id;
	@NotEmpty(message="name should not be null")
	private String name;
	//@SuppressWarnings("deprecation")
	//@SuppressWarnings("deprecation")
	@Email
	@NotEmpty(message = "Email should not be null")
	private String email;
	//@SuppressWarnings("deprecation")
	@NotNull
	@Size(min = 10,message = "comment body must be minimum 10 characters")
	private String body;
	//private Post post;
	
	


}
