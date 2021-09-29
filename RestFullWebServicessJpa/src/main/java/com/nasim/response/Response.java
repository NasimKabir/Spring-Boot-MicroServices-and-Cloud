package com.nasim.response;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nasim.exception.FieldValidationError;

import lombok.Data;
@Data
public class Response {

	private Date timeStamp;
	
	private int status;
	
	private String title;
	
	//private String detail;
	
	private Map<String,List<FieldValidationError>>errors= new HashMap<String, List<FieldValidationError>>();

	
}
