package com.nasim.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public Resource<User> findOne(@PathVariable int id) {
		User user = userService.findOne(id);
		if (user == null) {
			throw new UserNotFoundException("id-" + id);
		}

		// implement hateoas like as html link use for rest api link

		Resource<User> resource = new Resource<User>(user);
		// Adding self link employee 'singular' resource
		// retrive all users link
		// Adding method link employee 'singular' resource
		Link links = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(UserController.class).GetAllUsers())
				.withRel("users");
		resource.add(links);

		// retrive user findby id
		Link links1 = ControllerLinkBuilder
				.linkTo(ControllerLinkBuilder.methodOn(UserController.class).findOne(user.getId())).withRel("users");
		resource.add(links1);
		// Adding self link employee collection resource

		return resource;

	}

	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userService.deleteById(id);
		if (user == null) {
			throw new UserNotFoundException("id-" + id);
		}

	}

	@PostMapping("/users")
	public ResponseEntity<Object> Save(@Valid @RequestBody User user) {
		User users = userService.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(users.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
