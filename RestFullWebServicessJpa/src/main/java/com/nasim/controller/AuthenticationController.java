package com.nasim.controller;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasim.resquest.LoginRequest;
import com.nasim.service.AuthenticationService;


@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*", maxAge = 3600,allowedHeaders = "*")
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;
	
	 @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest login, HttpServletRequest request, HttpServletResponse response) throws InvalidKeySpecException, NoSuchAlgorithmException{
	        return authenticationService.login(login);
	    }

}
