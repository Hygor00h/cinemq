package com.cinemamq.cinemamq.infrastructure.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
public class RestErrorMessage {
	private HttpStatus status;
	private String message;

	public RestErrorMessage(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public RestErrorMessage() {
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
