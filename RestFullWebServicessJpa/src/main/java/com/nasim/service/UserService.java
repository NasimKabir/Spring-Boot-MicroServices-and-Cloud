package com.nasim.service;

import java.util.List;
import static org.springframework.http.HttpStatus.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nasim.model.User;
import com.nasim.repository.UserRepository;
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public List<User> findAll() {
		List<User>userList=userRepository.findAll();
		return userList;
	}

	public User save(User user) {
		if (userRepository.existsByUsername(user.getUsername())) {
			throw new RuntimeException(user.getUsername() + " doesn't exists !");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		 userRepository.save(user);
		return user;
	}
	
	public User update(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		 userRepository.save(user);
		return user;
	}

	public void deleteByTodoId(long id) {
		Long user = userRepository.findById(id).get().getId();
		if (user != null) {
			userRepository.deleteById(id);
		}
	}

	public User findByTodoId(long id) {
		return userRepository.findById(id).get();
	}
}
