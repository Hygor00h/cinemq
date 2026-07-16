package com.cinemamq.cinemamq.infrastructure.exception;

public class EventeNotFoundException  extends RuntimeException{

	public EventeNotFoundException(){
		super("Event Not Found");
	}

	public EventeNotFoundException(String message){
		super(message);
	}

}
