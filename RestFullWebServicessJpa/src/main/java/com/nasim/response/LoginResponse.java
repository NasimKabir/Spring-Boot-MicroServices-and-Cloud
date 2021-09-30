package com.nasim.response;

import lombok.Data;

@Data
public class LoginResponse {
	private String accessToken;
	private String username;
	
	private Object roles;
}