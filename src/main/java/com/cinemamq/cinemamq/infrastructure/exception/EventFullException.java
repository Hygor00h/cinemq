package com.cinemamq.cinemamq.infrastructure.exception;

public class EventFullException extends RuntimeException {

	public EventFullException(){
		super("Evento está cheio!");
	}

	public EventFullException(String message) {
		super(message);
	}
}
