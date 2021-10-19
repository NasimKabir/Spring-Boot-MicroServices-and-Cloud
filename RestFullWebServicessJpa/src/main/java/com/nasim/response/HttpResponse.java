package com.nasim.response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.nasim.exception.FieldValidationError;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class HttpResponse {

	private Date timeStamp;
	
	private int status;
	
	private String title;
	
	//private String detail;
	
	private Map<String,List<FieldValidationError>>errors= new HashMap<String, List<FieldValidationError>>();

	public HttpResponse(Date timeStamp, int status, String title) {
		this.timeStamp = timeStamp;
		this.status = status;
		this.title = title;
	}

	public HttpResponse(int status, String title) {
		this.status = status;
		this.title = title;
	}

}
