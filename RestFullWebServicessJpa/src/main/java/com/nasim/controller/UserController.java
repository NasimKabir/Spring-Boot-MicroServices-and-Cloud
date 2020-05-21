package com.nasim.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasim.exceptionHandeling.UserNotFoundException;
import com.nasim.model.User;
import com.nasim.repository.UserRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/users")
	public ResponseEntity<User> createdPost(@Valid @RequestBody User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new RuntimeException(user.getUsername() + " doesn't exists !");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return new ResponseEntity<User>(user, HttpStatus.CREATED);
	}

	
	@GetMapping("/users")
	public List<User> GetAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public User findOne(@PathVariable int id) {
		return userRepository.findById(id)
				.orElseThrow(()->new UserNotFoundException("id-" + id));
	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userRepository.findById(id)
				.orElseThrow(()->new UserNotFoundException("id-" + id));
		userRepository.deleteById(id);
	}

	
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user,@PathVariable("id")int id) {
		 userRepository.save(user);
		
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}
