package com.springboot.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
	
	private UserDetailsService detailsService;
	
	private JwtAuthenticationEntryPoint authenticationEntryPoint;
	
	
	private JwtAuthenticationFilter authenticationFilter;
	
	

	public SecurityConfig(UserDetailsService detailsService, JwtAuthenticationEntryPoint authenticationEntryPoint,
			JwtAuthenticationFilter authenticationFilter) {
		
		this.detailsService = detailsService;
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.authenticationFilter = authenticationFilter;
	}


	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		
	}
	
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeHttpRequests(
				(authorize)->authorize
				.requestMatchers(HttpMethod.GET,"api/**").permitAll()
				.requestMatchers("/api/auth/**").permitAll()
				.anyRequest().authenticated()
		//authorize.anyRequest().authenticated()
				).exceptionHandling(
				exception->exception
				.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session->
				session.sessionCreationPolicy
				(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		
		
		
		
		return http.build();
		
	}
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails pradip=User.builder()
//				.username("pradip")
//				.password(passwordEncoder().encode("pradip"))
//				.roles("USER")
//				.build();
//		UserDetails admin=User.builder()
//				.username("admin")
//				.password(passwordEncoder().encode("admin"))
//				.roles("ADMIN")
//				.build();
//
//		return new InMemoryUserDetailsManager(pradip,admin);
//
//	}
	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	
	

}
