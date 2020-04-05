package com.nasim.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nasim.exceptionHandeling.UserNotFoundException;
import com.nasim.model.User;
import com.nasim.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public List<User> GetAllUsers() {
		return userService.userList();
	}

	@GetMapping("/users/{id}")
	public User findOne(@PathVariable int id) {
		User user=userService.findOne(id);
		if(user==null) {
			throw new UserNotFoundException("id -"+id);
		}
		
		return user;
		
	}

	@PostMapping("/users")
	public ResponseEntity<Object> Save(@RequestBody User user) {
		User users = userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(users.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
