package com.nasim.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.nasim.model.User;

@Component
public class UserService {
	public static List<User> users = new ArrayList<>();
	public static int countId = 3;
	static {
		users.add(new User(1, "Nasim", new Date()));
		users.add(new User(2, "Nayeem", new Date()));
		users.add(new User(3, "Raihan", new Date()));
	}

	public List<User> userList() {
		return users;
	}

	public User save(User user) {
		if (user == null) {
			user.setId(++countId);
		}
		users.add(user);
		return user;
	}

	public User findOne(int id) {
		for (User user : users) {
			if (user.getId() == id) {
				return user;
			}
		}
		return null;
	}

	public User deleteById(int id) {
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			User user = iterator.next();
			if (user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		return null;
	}
}
