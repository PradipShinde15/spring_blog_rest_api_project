package com.springboot.blog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.blog.payload.JwtAuthResponse;
import com.springboot.blog.payload.LogInDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.service.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {

		this.authService = authService;
	}

	// build login rest api
	@PostMapping(value = { "/logIn", "/signIn" })
	public ResponseEntity<JwtAuthResponse> logIn(@RequestBody LogInDto logInDto) {

		String token = authService.logIn(logInDto);

		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setAccessToken(token);
		return ResponseEntity.ok(authResponse);

	}

	@PostMapping(value = { "/register", "/signUp" })
	public ResponseEntity<String> signUp(@RequestBody SignUpDto signUpDto) {

		String response = authService.signUp(signUpDto);
		return new ResponseEntity<String>(response, HttpStatus.CREATED);

	}

}
