package com.springboot.blog.service;

import com.springboot.blog.payload.LogInDto;
import com.springboot.blog.payload.SignUpDto;

public interface AuthService {
	
	String logIn(LogInDto logInDto);
	
	String signUp(SignUpDto signUpDto);
		 
	

}
