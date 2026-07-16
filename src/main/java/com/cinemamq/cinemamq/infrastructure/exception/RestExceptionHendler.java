package com.cinemamq.cinemamq.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHendler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EventeNotFoundException.class)
	public ResponseEntity<RestErrorMessage> eventNotFoundHandler(EventeNotFoundException e) {
		RestErrorMessage error = new RestErrorMessage(HttpStatus.NOT_FOUND,e.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(EventFullException.class)
	public ResponseEntity<RestErrorMessage> eventFullHandler(EventFullException e){
		RestErrorMessage error = new RestErrorMessage(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<RestErrorMessage> globalExceptionHandler(Exception e) {
		RestErrorMessage error = new RestErrorMessage(
						HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
