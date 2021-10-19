package com.nasim.controller;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import java.util.List;

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

import com.nasim.model.Todo;
import com.nasim.model.User;
import com.nasim.service.TodoService;
import com.nasim.service.UserService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("http://localhost:3000")
public class TodoController {
	@Autowired
	private TodoService todoService;

	@PostMapping("/todos")
	public ResponseEntity<Todo> createdTodo(@Valid @RequestBody Todo todos) {
		todos = todoService.save(todos);
		return new ResponseEntity<Todo>(todos, CREATED);
	}

	@GetMapping("/todos")
	public ResponseEntity<List<Todo>> GetAllTodos() {
		List<Todo> todos = todoService.findAllTodos();
		return new ResponseEntity<>(todos, OK);
	}

	@GetMapping("/todos/{id}")
	public ResponseEntity<?> findOne(@PathVariable int id) {
		Todo todos = todoService.findByTodoId(id);
		return new ResponseEntity<>(todos, OK);
	}

	@PutMapping("/todos/{id}")
	public ResponseEntity<Todo> updateUser(@Valid @RequestBody Todo todo, @PathVariable("id") int id) {
		todo = todoService.update(todo);

		return new ResponseEntity<Todo>(todo, OK);
	}

	@DeleteMapping("/todos/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") int id) {
		todoService.deleteByTodoId(id);
		return new ResponseEntity<Void>(ACCEPTED);
	}

}
