package com.nasim.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nasim.model.Todo;
import com.nasim.repository.TodoJpaRepository;

@Service
public class TodoService {
	@Autowired
	private TodoJpaRepository todoJpaRepository;

	public List<Todo> findAllTodos() {
		return todoJpaRepository.findAll();
	}

	public Todo save(Todo todo) {
		todo.setDone(true);
		todo.setTargetDate(new Date());
		return todoJpaRepository.save(todo);
	}
	
	public Todo update(Todo todo) {
		return todoJpaRepository.save(todo);
	}

	public void deleteByTodoId(long id) {
		Todo todo = todoJpaRepository.findById(id).get();
		if (todo != null) {
			todoJpaRepository.deleteById(id);
		}
	}

	public Todo findByTodoId(long id) {
		return todoJpaRepository.findById(id).get();
	}
}
