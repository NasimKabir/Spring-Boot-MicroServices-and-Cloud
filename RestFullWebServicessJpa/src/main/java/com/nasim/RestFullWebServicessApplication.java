package com.nasim;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.nasim.model.Role;
import com.nasim.repository.RoleRepository;

@SpringBootApplication
public class RestFullWebServicessApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestFullWebServicessApplication.class, args);
	}
/*
	@Autowired
	private RoleRepository roleRepository;

	@PostConstruct
	protected void init() { // add user role 
		List<Role> roleList =new ArrayList<>();
		roleList.add(new Role("ROLE_ADMIN"));
		roleList.add(new Role("ROLE_USER"));
		roleRepository.saveAll(roleList);
	}
*/
}
