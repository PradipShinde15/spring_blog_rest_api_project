package com.springboot.blog.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.springboot.blog.payload.ErrorDetails;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	//will handle specific exception and global exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException ex,WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false));
        
		
		return new ResponseEntity<ErrorDetails>(errorDetails ,HttpStatus.NOT_FOUND);
		
	}
	
	
	@ExceptionHandler(BlogApiException.class)
	public ResponseEntity<ErrorDetails> handleBlogApiException(BlogApiException ex,WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false));
        
		
		return new ResponseEntity<ErrorDetails>(errorDetails ,HttpStatus.NOT_FOUND);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGolbalException(Exception ex,WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false));
        
		
		return new ResponseEntity<ErrorDetails>(errorDetails ,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> errors=new HashMap<String, String>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName=((FieldError)error).getField();
			String message= error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		return new ResponseEntity<Object>(errors,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex,WebRequest webRequest){
        ErrorDetails errorDetails=new ErrorDetails(new Date(), ex.getMessage(), webRequest.getDescription(false));
        
		
		return new ResponseEntity<ErrorDetails>(errorDetails ,HttpStatus.UNAUTHORIZED);
		
	}
	

}
