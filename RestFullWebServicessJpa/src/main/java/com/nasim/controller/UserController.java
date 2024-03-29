package com.nasim.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nasim.model.User;
import com.nasim.resquest.LoginRequest;
import com.nasim.service.UserService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
//@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest login, HttpServletRequest request,HttpServletResponse response) {
		return userService.login(login);
	}

	@PostMapping("/register")
	public ResponseEntity<User> createdPost(@Valid @RequestBody User user) {
		user = userService.save(user);
		return new ResponseEntity<User>(user, CREATED);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> GetAllUsers() {
		List<User> users = userService.findAll();
		return new ResponseEntity<>(users, OK);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> findOne(@PathVariable int id) {
		User user = userService.findByTodoId(id);
		return new ResponseEntity<>(user, OK);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@Valid @RequestBody User user, @PathVariable("id") int id) {
		user = userService.update(user);

		return new ResponseEntity<User>(user, OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
		userService.deleteByTodoId(id);
		return new ResponseEntity<Void>(ACCEPTED);
	}

}
