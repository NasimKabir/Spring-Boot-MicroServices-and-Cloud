package com.nasim.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nasim.model.Todo;

public interface TodoJpaRepository extends JpaRepository<Todo, Long> {
	List<Todo> findByName(String username);
}
