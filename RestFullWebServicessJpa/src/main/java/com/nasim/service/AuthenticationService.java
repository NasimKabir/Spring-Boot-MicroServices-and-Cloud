package com.nasim.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nasim.model.User;
import com.nasim.response.LoginResponse;
import com.nasim.resquest.LoginRequest;
import com.nasim.utility.JwtTokenProvider;


@Service
@Transactional
public class AuthenticationService {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;


	public ResponseEntity<?> login(LoginRequest loginRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		User user = (User) authentication.getPrincipal();
		String jwt = jwtTokenProvider.generateToken(user.getUsername());
		List<String> roles = user.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAccessToken(jwt);
		loginResponse.setUsername(user.getUsername());
		loginResponse.setRoles(roles);
		
		return ResponseEntity.ok(loginResponse);

	}

}
