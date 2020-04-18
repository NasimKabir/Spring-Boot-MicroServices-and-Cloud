package com.nasim.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExceptionResponse {
	private Date timestamp;
	private String message;
	private String details;

	
	
	
	
	
	public ExceptionResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public String getMessage() {
		return message;
	}
	public String getDetails() {
		return details;
	}
	
	
	
	
}
