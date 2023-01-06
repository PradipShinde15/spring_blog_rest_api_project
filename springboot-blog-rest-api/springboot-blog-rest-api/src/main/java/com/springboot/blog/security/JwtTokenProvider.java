package com.springboot.blog.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.springboot.blog.exception.BlogApiException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtTokenProvider {
    
	@Value("${app.jwt.secret}")
	private String jwtSecret;
	@Value("${app.jwt.expiration-miliseconds}")
	private long jwtExpirationDate;
	
	//generate jwt Token
	
	public String generateToken(Authentication authentication ) {
		String username= authentication.getName();
		Date currentDate=new Date();
		Date expirationdate= new Date(currentDate.getTime()+jwtExpirationDate);
		String token =Jwts.builder()
		.setSubject(username)
		.setIssuedAt(new Date())
		.setExpiration(expirationdate)
		.signWith(key())
		.compact();
		
		return token;
		
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
		
	}
	
	
	//get username from jwt token
	public String getUsername(String token) {
		Claims claims = Jwts.parserBuilder()
		.setSigningKey(key()).build()
		.parseClaimsJws(token)
		.getBody();
		String userName=claims.getSubject();
		return userName;
		
	}
//	public String getUsername(String token) {
//		Claims claims = Jwts.parser().setSigningKey(key())
//		.parseClaimsJwt(token)
//		.getBody();
//		String userName=claims.getSubject();
//		return userName;
//		
//	}
	
	//validate jwt token
	
	public boolean validateToken(String token) {
		
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parseClaimsJws(token);
			
			return true;
			
		} catch (MalformedJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid jwt token");
			
		}catch (ExpiredJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "expired jwt token");
		}catch (UnsupportedJwtException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, "unsupported jwt token");
		}catch (IllegalArgumentException ex) {
			throw new BlogApiException(HttpStatus.BAD_REQUEST, " jwt claim string is empty");
		}
//		try {
////			Jwts.parserBuilder()
////			.setSigningKey(key())
////			.build()
////			.parse(token);
//			Jwts.parser()
//		    .setSigningKey(key())
//		    .parseClaimsJws(token)
//		    .getBody();
//
//			
//			return true;
//			
//		} catch (MalformedJwtException ex) {
//			throw new BlogApiException(HttpStatus.BAD_REQUEST, "Invalid jwt token");
//			
//		}catch (ExpiredJwtException ex) {
//			throw new BlogApiException(HttpStatus.BAD_REQUEST, "expired jwt token");
//		}catch (UnsupportedJwtException ex) {
//			throw new BlogApiException(HttpStatus.BAD_REQUEST, "unsupported jwt token");
//		}catch (IllegalArgumentException ex) {
//			throw new BlogApiException(HttpStatus.BAD_REQUEST, " jwt claim string is empty");
//		}
//		
//		
		
	}
}
