package com.springboot.blog.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogApiException;
import com.springboot.blog.payload.LogInDto;
import com.springboot.blog.payload.SignUpDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.security.JwtTokenProvider;
import com.springboot.blog.service.AuthService;
@Service
public class AuthServiceImpl implements AuthService{
	
	private AuthenticationManager authenticationManager;
	
	private UserRepository userRepository;
	
	private RoleRepository roleRepository;
	
	private PasswordEncoder passwordEncoder;
	
	private JwtTokenProvider jwtTokenProvider;

	



	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
		
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	public String logIn(LogInDto logInDto) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(logInDto.getUsernameOrEmail(), logInDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token=jwtTokenProvider.generateToken(authentication);
		return token;
	}

	@Override
	public String signUp(SignUpDto signUpDto) {
		
		if(userRepository.existsByUsername(signUpDto.getUsername())) {
			throw new  BlogApiException(HttpStatus.BAD_REQUEST,  "username is already exist..!");
		}
		
		if(userRepository.existsByEmail(signUpDto.getEmail())) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email is already exist");
			
		}
		
		User user=new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		Set<Role> roles=new HashSet<>();
		Role userRole=roleRepository.findByName("ROLE_USER").get();
		roles.add(userRole);
		user.setRoles(roles);
		
		userRepository.save(user);
	
		return "User register successfully";
	}

}
